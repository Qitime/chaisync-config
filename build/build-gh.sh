#!/usr/bin/env bash
# 打电视端（母 APK）：把公钥、master、仓库地址钉进 assets，再追加一个 classes6.dex。
#
# 与 build.ps1 等价，区别只是用 bash 直调 java（本机 PowerShell 在沙箱里起不了子进程）。
#
# 用法：
#   bash build/build-gh.sh <MASTER_B64> <PUBKEY_HEX> <RAW_URL>
# 可选环境变量：
#   APK=/path/to/original.apk   MIRRORS="a,b,c"   OUT=dist/xxx.apk
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && { pwd -W 2>/dev/null || pwd; })"
ROOT="$(dirname "$HERE")"

MASTER_B64="${1:?需要 MASTER_B64}"
PUB_HEX="${2:?需要 PUBKEY_HEX}"
RAW_URL="${3:?需要 RAW_URL}"

APK="${APK:-C:/Users/qiqi/Downloads/ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk}"
MIRRORS="${MIRRORS:-https://ghfast.top/,https://gh-proxy.com/,https://ghproxy.net/}"
BT="${BUILD_TOOLS:-D:/Ai/Android/sdk/build-tools/37.0.0}"
ANDROID_JAR="${ANDROID_JAR:-D:/Ai/Android/sdk/platforms/android-36/android.jar}"
APKTOOL="${APKTOOL:-D:/Ai/tools/apkrev-tools/apktool/apktool.jar}"
PY="${PYTHON:-C:/Users/qiqi/.workbuddy/binaries/python/versions/3.13.12/python.exe}"
KS="$HERE/chaichai.keystore"
OUT="${OUT:-$ROOT/dist/ChaiSync-tv-1.0.apk}"

[ -f "$APK" ]      || { echo "找不到原包：$APK" >&2; exit 1; }
[ -f "$APKTOOL" ]  || { echo "找不到 apktool：$APKTOOL" >&2; exit 1; }
[ -f "$BT/lib/d8.jar" ] || { echo "找不到 d8.jar" >&2; exit 1; }

WORK="$HERE/work-$(date +%Y%m%d-%H%M%S)"
mkdir -p "$WORK/classes" "$WORK/dexout" "$ROOT/dist"
echo "      工作目录 $WORK"

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
}
os.makedirs(os.path.dirname(out), exist_ok=True)
json.dump(cfg, open(out, "w", encoding="utf-8"), ensure_ascii=False, indent=2)
print("      mirrors=%d  rawUrl=%s" % (len(cfg["mirrors"]), raw))
PYEOF

if [ ! -d "$HERE/decoded" ]; then
  echo "[2/7] apktool d -s"
  java -jar "$APKTOOL" d -f -s -o "$HERE/decoded" "$APK" > "$WORK/apktool-d.log" 2>&1 \
    || { tail -20 "$WORK/apktool-d.log" >&2; exit 1; }
else
  echo "[2/7] 复用已解包目录"
fi

echo "[3/7] 注入 provider"
"$PY" - "$HERE/decoded/AndroidManifest.xml" <<'PYEOF'
import sys
p = sys.argv[1]
t = open(p, encoding="utf-8").read()
tag = 'com.dh.myembyapp.sync.SyncProvider'
if tag in t:
    print("      manifest 已包含 provider")
else:
    prov = ('    <provider android:name="com.dh.myembyapp.sync.SyncProvider" '
            'android:authorities="com.dh.myembyapp.sync.provider" '
            'android:exported="false" android:initOrder="1000" />\n')
    t = t.replace('</application>', prov + '</application>')
    open(p, "w", encoding="utf-8").write(t)
    print("      manifest patched")
PYEOF

echo "[4/7] apktool b"
java -jar "$APKTOOL" b -f -o "$WORK/patched.apk" "$HERE/decoded" > "$WORK/apktool-b.log" 2>&1 \
  || { tail -30 "$WORK/apktool-b.log" >&2; exit 1; }

echo "[5/7] javac + d8"
find "$ROOT/app/src" -name '*.java' > "$WORK/sources.txt"
javac --release 8 -Xlint:-options -encoding UTF-8 \
  -classpath "$ANDROID_JAR" -d "$WORK/classes" @"$WORK/sources.txt"
jar cf "$WORK/patch.jar" -C "$WORK/classes" .
java -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 \
  --min-api 23 --lib "$ANDROID_JAR" --output "$WORK/dexout" "$WORK/patch.jar"

echo "[6/7] splice + zipalign"
[ -f "$WORK/dexout/classes.dex" ] || { echo "d8 没产出 dex" >&2; exit 1; }
"$PY" "$HERE/splice.py" "$APK" "$WORK/patched.apk" "$WORK/dexout/classes.dex" \
  "$ROOT/app/assets/sync_config.json" "$WORK/final-unaligned.apk"
"$BT/zipalign.exe" -p -f 4 "$WORK/final-unaligned.apk" "$WORK/final-aligned.apk"

echo "[7/7] apksigner"
if [ ! -f "$KS" ]; then
  keytool -genkeypair -keystore "$KS" -alias chaichai -keyalg RSA -keysize 2048 -validity 10000 \
    -storepass chaichai123 -keypass chaichai123 \
    -dname "CN=ChaiSync, OU=Dev, O=Personal, L=City, ST=State, C=CN" >/dev/null 2>&1
  echo "      新 keystore: $KS（丢了就无法覆盖升级）"
fi
java -jar "$BT/lib/apksigner.jar" sign --ks "$KS" --ks-key-alias chaichai \
  --ks-pass pass:chaichai123 --key-pass pass:chaichai123 \
  --out "$OUT" "$WORK/final-aligned.apk"

echo
echo "OK -> $OUT"
ls -lh "$OUT"
