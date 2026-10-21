#!/usr/bin/env bash
# 打电视端（母 APK）：把公钥、master、仓库地址钉进 assets，再追加一个 classes6.dex。
#
# 与 build.ps1 等价，区别只是用 bash 直调 java（本机 PowerShell 在沙箱里起不了子进程）。
#
# 用法（推荐：密钥目录，只认一处）：
#   bash build/build-gh.sh --keys keys-prod "<RAW_URL>"
#
#   密钥目录里必须有 pubkey.hex 与 master.b64。默认读 keys-prod/（线上发布用的那套），
#   想用别的就 --keys keys2。--keys 与位置参数可以混用，显式给了就覆盖目录里的值。
#
# 用法（老式，三个位置参数）：
#   bash build/build-gh.sh <MASTER_B64> <PUBKEY_HEX> <RAW_URL>
#
#   ⚠ 2026-10-21 的 1.3 就是因为这里传了 keys/ 的参数、而线上信封是 keys-prod/ 签的，
#     打出一个永远 bad-signature 的包。现在签名后会自动跑 tools/check-keys.mjs 自检，
#     对不上直接失败——别再用眼睛核对公钥。
#
# 可选环境变量：
#   APK=/path/to/original.apk   MIRRORS="a,b,c"   OUT=dist/xxx.apk
#   KEYDIR=keys-prod   密钥目录（等价于 --keys）
#   PKG=com.xemby.tv   改包名，做出能和原版柴柴并存的一份（改完必须重签名）
#   FORCE_DECODE=1     强制重新 apktool d（默认按源包 sha 复用 build/decoded）
#   FORCE_BUILD=1      强制重新 apktool b（默认按清单/资源指纹复用 build/cache/patched.apk）
#   SKIP_KEY_CHECK=1   跳过构建后的密钥自检（仅限离线排障，发版别用）
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && { pwd -W 2>/dev/null || pwd; })"
ROOT="$(dirname "$HERE")"

# ---- 参数：--keys 与三个位置参数都能用，位置参数优先 ----
KEYDIR="${KEYDIR:-keys-prod}"
POS=()
while [ $# -gt 0 ]; do
  case "$1" in
    --keys)   KEYDIR="${2:?--keys 后面要跟目录}"; shift 2 ;;
    --keys=*) KEYDIR="${1#--keys=}"; shift ;;
    -h|--help)
      sed -n '2,30p' "$0" | sed 's/^# \{0,1\}//'
      exit 0 ;;
    *)        POS+=("$1"); shift ;;
  esac
done

# 密钥来源：位置参数 > 密钥目录 > 报错
if [ "${#POS[@]}" -ge 2 ]; then
  MASTER_B64="${POS[0]}"
  PUB_HEX="${POS[1]}"
  RAW_URL="${POS[2]:-}"
  KEY_SRC="位置参数"
else
  RAW_URL="${POS[0]:-}"
  KD="$KEYDIR"
  case "$KD" in /*|[A-Za-z]:*) ;; *) KD="$ROOT/$KD" ;; esac
  [ -f "$KD/pubkey.hex" ] && [ -f "$KD/master.b64" ] \
    || { echo "密钥目录缺文件：$KD 需要 pubkey.hex + master.b64（或直接传三个位置参数）" >&2; exit 1; }
  MASTER_B64="$(tr -d '\r\n' < "$KD/master.b64")"
  PUB_HEX="$(tr -d '\r\n' < "$KD/pubkey.hex")"
  KEY_SRC="$KD"
fi

RAW_URL="${RAW_URL:-https://raw.githubusercontent.com/Qitime/chaisync-config/main/dist/config.enc.json}"

[ -n "$MASTER_B64" ] || { echo "MASTER_B64 为空" >&2; exit 1; }
[ -n "$PUB_HEX" ]    || { echo "PUBKEY_HEX 为空" >&2; exit 1; }

PKG="${PKG:-com.dh.myembyapp}"

APK="${APK:-C:/Users/qiqi/Downloads/ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk}"
MIRRORS="${MIRRORS:-https://ghfast.top/,https://gh-proxy.com/,https://ghproxy.net/}"
BT="${BUILD_TOOLS:-D:/Ai/Android/sdk/build-tools/37.0.0}"
ANDROID_JAR="${ANDROID_JAR:-D:/Ai/Android/sdk/platforms/android-36/android.jar}"
APKTOOL="${APKTOOL:-D:/Ai/tools/apkrev-tools/apktool/apktool.jar}"
PY="${PYTHON:-C:/Users/qiqi/.workbuddy/binaries/python/versions/3.13.12/python.exe}"
KS="$HERE/xemby.keystore"
OUT="${OUT:-$ROOT/dist/Xemby-tv-1.2.apk}"

[ -f "$APK" ]      || { echo "找不到原包：$APK" >&2; exit 1; }
[ -f "$APKTOOL" ]  || { echo "找不到 apktool：$APKTOOL" >&2; exit 1; }
[ -f "$BT/lib/d8.jar" ] || { echo "找不到 d8.jar" >&2; exit 1; }

echo "      密钥来源 $KEY_SRC"
echo "      pubKey   ${PUB_HEX:0:24}…${PUB_HEX: -8}"
echo "      master   ${MASTER_B64:0:8}…"

WORK="$HERE/work-$(date +%Y%m%d-%H%M%S)"
mkdir -p "$WORK/classes" "$WORK/dexout" "$ROOT/dist"
echo "      工作目录 $WORK"

# ---- 解码基座 / 打包缓存 ----
# decoded/           一次 apktool d -s 的产物；源 APK 与 apktool 不变就永久复用
# cache/patched.apk  apktool b 的产物（已注入 provider / 改 label / 可选 retarget）
# 键 = 源包 + apktool + PKG + 解码树指纹；任何一项变了自动重建
# FORCE_DECODE=1 强制重解；FORCE_BUILD=1 强制重打（手改过 res 时用）
BASE="$HERE/decoded"
BASE_STAMP="$BASE/.base.stamp"
BASE_MANIFEST="$BASE/.manifest.orig"
CACHE="$HERE/cache"
PATCHED="$CACHE/patched.apk"
PATCHED_STAMP="$CACHE/patched.stamp"
APK_SHA="$(sha256sum "$APK" | cut -d' ' -f1)"
APKTOOL_SHA="$(sha256sum "$APKTOOL" | cut -d' ' -f1)"
DECODE_KEY="$APK_SHA $APKTOOL_SHA"

# 解码树指纹：路径+大小（跳过 build/ 与缓存文件）叠上清单正文哈希。
# 手改了 decoded/res 里的资源 → 指纹变 → 自动重打，不会拿旧包糊弄。
tree_fingerprint() {
  "$PY" - "$1" <<'PYEOF'
import hashlib, os, sys
root = sys.argv[1]
skip = {"build", ".base.stamp", ".manifest.orig", ".manifest.orig.tmp"}
rows = []
for dirpath, dirnames, filenames in os.walk(root):
    dirnames[:] = [d for d in dirnames if d not in skip]
    for fn in filenames:
        if fn in skip: continue
        p = os.path.join(dirpath, fn)
        try: st = os.stat(p)
        except OSError: continue
        rows.append("%s\t%d" % (os.path.relpath(p, root).replace("\\", "/"), st.st_size))
rows.sort()
h = hashlib.sha256("\n".join(rows).encode()).hexdigest()
man = os.path.join(root, "AndroidManifest.xml")
if os.path.exists(man):
    h += ":" + hashlib.sha256(open(man, "rb").read()).hexdigest()
print(h[:24])
PYEOF
}

echo "[1/7] 写 assets/sync_config.json"
"$PY" - "$ROOT/app/assets/sync_config.json" "$MASTER_B64" "$PUB_HEX" "$RAW_URL" "$MIRRORS" <<'PYEOF'
import json, sys, os
out, master, pub, raw, mirrors = sys.argv[1:6]
cfg = {
    "enabled": True,
    "rawUrl": raw,
    "pubKeyHex": pub,
    "masterB64": master,
    "mirrors": [m.strip() for m in mirrors.split(",") if m.strip()],
    "probeTcpMs": 1200,
    "probeHttpMs": 1500,
    "fetchBudgetMs": 9000,
    "fetchTryMs": 3200,
    "fetchStaggerMs": 900,
}
os.makedirs(os.path.dirname(out), exist_ok=True)
json.dump(cfg, open(out, "w", encoding="utf-8"), ensure_ascii=False, indent=2)
print("      mirrors=%d  rawUrl=%s" % (len(cfg["mirrors"]), raw))
PYEOF

if [ -z "${FORCE_DECODE:-}" ] && [ -f "$BASE_STAMP" ] && [ -f "$BASE_MANIFEST" ] \
   && [ -f "$BASE/AndroidManifest.xml" ] && [ "$(cat "$BASE_STAMP")" = "$DECODE_KEY" ]; then
  echo "[2/7] 复用解码基座（源包未变，跳过 apktool d）"
else
  echo "[2/7] apktool d -s（首次构建，或源包/apktool 变了）"
  rm -rf "$BASE.new"
  java -jar "$APKTOOL" d -f -s -o "$BASE.new" "$APK" > "$WORK/apktool-d.log" 2>&1 \
    || { tail -20 "$WORK/apktool-d.log" >&2; exit 1; }
  rm -rf "$BASE.old"
  if [ -d "$BASE" ]; then mv "$BASE" "$BASE.old"; fi
  mv "$BASE.new" "$BASE"
  rm -rf "$BASE.old"
  cp "$BASE/AndroidManifest.xml" "$BASE_MANIFEST"
  printf '%s' "$DECODE_KEY" > "$BASE_STAMP"
fi

echo "[3/7] 注入 provider + 改应用名"
MANIFEST="$BASE/AndroidManifest.xml"
# 无条件还原原始清单：上一次构建若中途失败，基座里可能还留着补丁版（provider/改名）
cp "$BASE_MANIFEST" "$MANIFEST"
cp "$MANIFEST" "$WORK/AndroidManifest.base.xml"
TREE_FP="$(tree_fingerprint "$BASE")"

# 清单是共享的构建输入，换包名只在这一步临时改，编译完立刻还原
"$PY" - "$MANIFEST" <<'PYEOF'
import sys
p = sys.argv[1]
t = open(p, encoding="utf-8").read()
changed = []

tag = 'com.dh.myembyapp.sync.SyncProvider'
if tag in t:
    print("      manifest 已包含 provider")
else:
    prov = ('    <provider android:name="com.dh.myembyapp.sync.SyncProvider" '
            'android:authorities="com.dh.myembyapp.sync.provider" '
            'android:exported="false" android:initOrder="1000" />\n')
    t = t.replace('</application>', prov + '</application>')
    changed.append("provider")

# 应用名改成字面量：只动 AndroidManifest，不碰 resources.arsc，
# 这样所有语言下显示的都是 Xemby，也不会因为重建资源表而改变其它资源 ID。
label = 'android:label="@string/app_name"'
if label in t:
    t = t.replace(label, 'android:label="Xemby"')
    changed.append("label->Xemby")

if changed:
    open(p, "w", encoding="utf-8", newline="").write(t)
    print("      manifest patched: " + ", ".join(changed))
PYEOF

if [ "$PKG" != "com.dh.myembyapp" ]; then
  echo "      换包名 -> $PKG"
  "$PY" "$HERE/retarget.py" "$MANIFEST" "$PKG"
fi

PATCH_KEY="$DECODE_KEY|$PKG|$TREE_FP"
if [ -z "${FORCE_BUILD:-}" ] && [ -f "$PATCHED" ] && [ -f "$PATCHED_STAMP" ] \
   && [ "$(cat "$PATCHED_STAMP")" = "$PATCH_KEY" ]; then
  echo "[4/7] 复用已打包基座（清单/资源/包名未变，跳过 apktool b）"
  cp "$PATCHED" "$WORK/patched.apk"
else
  echo "[4/7] apktool b"
  java -jar "$APKTOOL" b -f -o "$WORK/patched.apk" "$BASE" > "$WORK/apktool-b.log" 2>&1 \
    || { tail -30 "$WORK/apktool-b.log" >&2; exit 1; }
  mkdir -p "$CACHE"
  cp "$WORK/patched.apk" "$PATCHED"
  printf '%s' "$PATCH_KEY" > "$PATCHED_STAMP"
fi
# 编译完把清单恢复成原始状态，避免影响下一次构建
cp "$WORK/AndroidManifest.base.xml" "$MANIFEST"

echo "[5/7] javac + d8"
find "$ROOT/app/src" -name '*.java' > "$WORK/sources.txt"
javac --release 8 -Xlint:-options -encoding UTF-8 \
  -classpath "$ANDROID_JAR" -d "$WORK/classes" @"$WORK/sources.txt"
jar cf "$WORK/patch.jar" -C "$WORK/classes" .
java -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 \
  --min-api 23 --lib "$ANDROID_JAR" --output "$WORK/dexout" "$WORK/patch.jar"

echo "[6/7] splice + 换图标 + zipalign"
[ -f "$WORK/dexout/classes.dex" ] || { echo "d8 没产出 dex" >&2; exit 1; }
"$PY" "$HERE/splice.py" "$APK" "$WORK/patched.apk" "$WORK/dexout/classes.dex" \
  "$ROOT/app/assets/sync_config.json" "$WORK/final-unaligned.apk"

# 品牌图：原包里 res/Y0.png = @drawable/chaichai，res/kK.png = @drawable/chaichai_b。
# 只换文件内容、不换资源名，所以 resources.arsc 不用重建。
BRAND_ICON="$HERE/branding/xemby_icon_1024.png"
BRAND_BANNER="$HERE/branding/xemby_banner_1280.png"
if [ -f "$BRAND_ICON" ] && [ -f "$BRAND_BANNER" ]; then
  echo "      替换图标与 TV banner"
  "$PY" "$HERE/zipswap.py" "$WORK/final-unaligned.apk" "$WORK/patched.apk" \
    "$BRAND_ICON" "$BRAND_BANNER" "$WORK/final-branded.apk"
  mv -f "$WORK/final-branded.apk" "$WORK/final-unaligned.apk"
else
  echo "      跳过品牌图（缺 build/branding/*.png）"
fi

"$BT/zipalign.exe" -p -f 4 "$WORK/final-unaligned.apk" "$WORK/final-aligned.apk"

echo "[7/7] apksigner"
if [ ! -f "$KS" ]; then
  keytool -genkeypair -keystore "$KS" -alias xemby -keyalg RSA -keysize 2048 -validity 10000 \
    -storepass xemby2026 -keypass xemby2026 \
    -dname "CN=Xemby, OU=Dev, O=Xemby, L=City, ST=State, C=CN" >/dev/null 2>&1
  echo "      新 keystore: $KS（丢了就无法覆盖升级）"
fi
java -jar "$BT/lib/apksigner.jar" sign --ks "$KS" --ks-key-alias xemby \
  --ks-pass pass:xemby2026 --key-pass pass:xemby2026 \
  --out "$OUT" "$WORK/final-aligned.apk"

# ---- 构建后自检：APK 里的公钥/master 必须能解开线上信封 ----
# 这一步就是防 1.3 那种事故的：编译、签名、安装全都正常，但装到电视上永远 bad-signature。
if [ -z "${SKIP_KEY_CHECK:-}" ] && command -v node >/dev/null 2>&1; then
  echo "[自检] 用 APK 内的密钥解一次线上信封"
  # 允许用本地信封副本（离线/网络受限时）：ENV_FILE=dist/config.enc.json
  CHECK_ARGS=(--apk "$OUT")
  [ -n "${ENV_FILE:-}" ] && CHECK_ARGS+=(--env "$ENV_FILE")
  set +e
  node "$ROOT/tools/check-keys.mjs" "${CHECK_ARGS[@]}"
  CHECK_RC=$?
  set -e
  if [ "$CHECK_RC" -eq 1 ]; then
    echo >&2
    echo "✗ 自检失败：这个包的密钥与线上信封对不上，装到电视上会静默不同步。" >&2
    echo "  产物留在 $OUT 仅供排查，不要发布。" >&2
    echo "  确认密钥目录：当前用的是 $KEY_SRC" >&2
    echo "  确实要跳过自检：SKIP_KEY_CHECK=1（离线排障用，发版别用）" >&2
    exit 1
  elif [ "$CHECK_RC" -eq 2 ]; then
    echo >&2
    echo "! 自检没能完成（拿不到信封，多半是网络），这不代表密钥错了。" >&2
    echo "  联网后单独补跑：node tools/check-keys.mjs --apk $OUT" >&2
    echo "  或指定本地信封：ENV_FILE=dist/config.enc.json bash build/build-gh.sh --keys keys-prod" >&2
    echo "  包已生成，但发布前必须补一次自检。" >&2
  elif [ "$CHECK_RC" -ne 0 ]; then
    echo "✗ 自检异常退出（$CHECK_RC）" >&2
    exit 1
  fi
else
  [ -n "${SKIP_KEY_CHECK:-}" ] && echo "[自检] 已按 SKIP_KEY_CHECK 跳过"
  command -v node >/dev/null 2>&1 || echo "[自检] 没有 node，跳过密钥自检（建议装 node 后重跑）"
fi

echo
echo "OK -> $OUT"
ls -lh "$OUT"

# 工作目录只留最近 3 个（每个 ~90MB，攒久了很占地方）
KEEP=3
ls -1dt "$HERE"/work-* 2>/dev/null | tail -n +$((KEEP + 1)) | while read -r d; do
  case "$d" in
    "$HERE"/work-*) rm -rf "$d" ;;
  esac
done
