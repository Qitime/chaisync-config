#!/usr/bin/env bash
# 一键安全发布信封（本地使用）。
# 流程：指纹台账 → 指纹断言 → 发布(seq 自动防回滚) → 验签 → 信封自检 → 推送。
#
# 用法：
#   tools/publish-safe.sh                     # 只生成 dist/config.enc.json 并自检
#   SEQ=7 tools/publish-safe.sh               # 显式指定 seq
#   tools/publish-safe.sh --push              # 生成后自动 commit + push 到 origin/main
#
# 缺省读取 keys-prod；刻意演练（如验证坏签效果）才用 --keys keys + --allow-key-mismatch。

set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

KEYS_DIR="${KEYS_DIR:-keys-prod}"
ALLOW_MISMATCH=0
DO_PUSH=0

for a in "$@"; do
  case "$a" in
    --push) DO_PUSH=1 ;;
    --keys) KEYS_DIR="${2:-keys-prod}"; shift ;;
    --allow-key-mismatch) ALLOW_MISMATCH=1 ;;
  esac
  shift || true
done

echo "== [1/5] 密钥组指纹台账（$KEYS_DIR）"
node tools/ensure-keyset.mjs --dir "$KEYS_DIR" --strict

SEQ_ARG=()
if [ -n "${SEQ:-}" ]; then SEQ_ARG=(--seq "$SEQ"); fi

echo "== [2/5] 发布信封（指纹断言 + seq 防回滚）"
LIVE_URL="$(node -e "const e=require('./config/expected-keyset.json');process.stdout.write((e.prod&&e.prod.rawUrl)||'')")"
if [ "${#SEQ_ARG[@]}" -eq 0 ] && [ ! -f dist/config.enc.json ]; then
  echo "  out 不存在，尝试从线上取 seq（$LIVE_URL）..."
fi
args=(node tools/publish.mjs publish --in config/config.json --out dist/config.enc.json --keys-dir "$KEYS_DIR")
if [ "$ALLOW_MISMATCH" = "1" ]; then args+=(--allow-key-mismatch); fi
if [ "${#SEQ_ARG[@]}" -gt 0 ]; then args+=("${SEQ_ARG[@]}"); fi
"${args[@]}"

echo "== [3/5] 本地验签"
node tools/publish.mjs verify --in dist/config.enc.json --keys-dir "$KEYS_DIR" >/dev/null
echo "  ✓ 验签通过"

echo "== [4/5] 信封自检（对期望台账）"
node tools/check-keys.mjs --envelope dist/config.enc.json --keys-dir "$KEYS_DIR" --expect prod | tail -3

echo "== [5/5] 完成"
SEQ="$(node -p "require('./dist/config.enc.json').seq")"
echo "  新信封 dist/config.enc.json  seq=$SEQ  fingerprint=$(node -e "const e=require('./keys-prod/keyset.json');process.stdout.write(e.fingerprint)")"
if [ "$DO_PUSH" = "1" ]; then
  git add dist/config.enc.json
  git commit -m "config: seq ${SEQ} (publish-safe)"
  git push
  echo "  ✓ 已推送到 origin/main"
else
  echo "  推送命令：git add dist/config.enc.json && git commit -m \"config: seq ${SEQ}\" && git push"
fi
