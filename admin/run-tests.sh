#!/usr/bin/env bash
#
# 子母 APK 的协议回归测试。
#
# 不需要 Android 设备、不需要模拟器、不联网：
# 用垫片提供的 android.util.Base64 / android.net.Uri / org.json，
# 把<b>两边的真实源码</b>（admin/src 的 EnvelopeSeal、app/src 的 Envelope）
# 编进同一个 JVM 里跑，所以测的是真代码，不是复制品。
#
# 用法：bash run-tests.sh
#
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && pwd -W)"
PARENT="$(cd "$HERE/.." && pwd -W)"
OUT="$HERE/build/test-out"
KEYS="$PARENT/keys"

say() { printf '\n\033[1;36m%s\033[0m\n' "$*"; }

[ -f "$KEYS/signing.pem" ] || { echo "缺少 $KEYS/signing.pem，先跑 node tools/publish.mjs genkey --out keys"; exit 1; }

say "[1/4] 编译（两边的真实源码 + 垫片）"
rm -rf "$OUT"
mkdir -p "$OUT"
javac --release 8 -Xlint:-options -encoding UTF-8 -d "$OUT" \
  $(find "$HERE/test/shim" -name '*.java') \
  "$HERE/src/com/dh/chaisync/admin/EnvelopeSeal.java" \
  "$HERE/src/com/dh/chaisync/admin/GitHubClient.java" \
  "$HERE/src/com/dh/chaisync/admin/Publisher.java" \
  "$PARENT/app/src/com/dh/myembyapp/sync/Envelope.java" \
  "$HERE/test/Harness.java" \
  "$HERE/test/ParentCheck.java" \
  "$HERE/test/E2E.java"
echo "  OK"

say "[2/4] 子 APK 封装 → 母 APK 解封"
java -Dfile.encoding=UTF-8 -cp "$OUT" com.dh.chaisync.admin.Harness \
  "$KEYS/signing.pem" "$KEYS/master.b64" "$HERE/build/env.json" 7 "$KEYS/pubkey.hex"
java -Dfile.encoding=UTF-8 -cp "$OUT" com.dh.myembyapp.sync.ParentCheck \
  "$HERE/build/env.json" "$KEYS/pubkey.hex" "$KEYS/master.b64" 0

say "[3/4] Node 端解封同一封信（证明三端同构）"
NODE_BIN="${NODE_BIN:-node}"
if ! command -v "$NODE_BIN" >/dev/null 2>&1; then
  for c in "$HOME"/.workbuddy/binaries/node/versions/*/node.exe "/c/Program Files/nodejs/node.exe"; do
    [ -f "$c" ] && NODE_BIN="$c" && break
  done
fi
if [ -n "$NODE_BIN" ] && command -v "$NODE_BIN" >/dev/null 2>&1; then
  (cd "$PARENT" && "$NODE_BIN" tools/publish.mjs verify \
     --in "$HERE/build/env.json" --pub "$KEYS/pubkey.hex" --master "$(cat "$KEYS/master.b64")" \
     2>&1 | head -4)
else
  echo "  跳过：没有 node"
fi

say "[4/4] 端到端：真实 Publisher + 模拟 GitHub API"
java -Dfile.encoding=UTF-8 -cp "$OUT" com.dh.chaisync.admin.E2E \
  "$KEYS/signing.pem" "$KEYS/master.b64" "$KEYS/pubkey.hex"

printf '\n\033[1;32m全部通过\033[0m\n'
