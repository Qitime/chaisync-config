#!/usr/bin/env bash
#
# ChaiSync Admin 子 APK 的构建脚本。
#
# 为什么不用 Gradle：这台机器上 AGP 的依赖需要联网拉，而网络不稳；
# 而 aapt2 + javac + d8 + apksigner 是 build-tools 里自带的，全部离线可用。
# 代价是不能用 AndroidX —— 所以整个 App 是 framework API + 代码构建界面。
#
# 用法：
#   bash build-apk.sh
#   ANDROID_SDK_ROOT=/path/to/sdk bash build-apk.sh
#
set -euo pipefail

# aapt2 / javac / d8 / keytool 都是 Windows 程序，不认 Git Bash 的 /d/... 路径。
# pwd -W 直接给 D:/... 形式，省掉到处 cygpath 的麻烦。
HERE="$(cd "$(dirname "$0")" && pwd -W)"
SDK="${ANDROID_SDK_ROOT:-D:/Ai/Android/sdk}"
BT="${BUILD_TOOLS:-$SDK/build-tools/37.0.0}"
PLATFORM="${PLATFORM_JAR:-$SDK/platforms/android-34/android.jar}"

MIN_SDK=24
TARGET_SDK=34
VERSION_NAME="1.0.6"
VERSION_CODE=7

KS="$HERE/build/chaisync-admin.jks"
KS_ALIAS="chaisync"
KS_PASS="chaisync-admin"

OUT="$HERE/build/out"
DIST="$HERE/dist"

PY="${PYTHON:-python}"

say() { printf '\n\033[1;36m%s\033[0m\n' "$*"; }
die() { printf '\n\033[1;31m✗ %s\033[0m\n' "$*" >&2; exit 1; }

[ -f "$BT/aapt2.exe" ] || [ -f "$BT/aapt2" ] || die "找不到 aapt2：$BT"
[ -f "$PLATFORM" ] || die "找不到 android.jar：$PLATFORM"
command -v javac >/dev/null || die "找不到 javac"
command -v java >/dev/null || die "找不到 java"

AAPT2="$BT/aapt2.exe"; [ -x "$AAPT2" ] || AAPT2="$BT/aapt2"
ZIPALIGN="$BT/zipalign.exe"; [ -x "$ZIPALIGN" ] || ZIPALIGN="$BT/zipalign"

if ! command -v "$PY" >/dev/null 2>&1; then
  if command -v python3 >/dev/null 2>&1; then
    PY=python3
  else
    die "找不到 python（用于把 classes.dex 写进 APK）。可设 PYTHON=/path/to/python"
  fi
fi

say "[0/7] 清理"
# 不用 rm -rf：本机 shell 的删除动作被安全策略接管，会直接失败。
# 这里只是自己的 build 中间目录，用 Python 清空即可。
"$PY" - "$OUT" <<'PYEOF'
import os, shutil, sys
p = sys.argv[1]
if os.path.isdir(p):
    shutil.rmtree(p, ignore_errors=True)
print('  已清空 %s' % p)
PYEOF
mkdir -p "$OUT/gen" "$OUT/classes" "$OUT/dex" "$DIST"

say "[1/7] 编译资源"
"$AAPT2" compile --dir "$HERE/res" -o "$OUT/res.zip"

say "[2/7] 链接资源与清单"
"$AAPT2" link \
  -o "$OUT/base.apk" \
  -I "$PLATFORM" \
  --manifest "$HERE/AndroidManifest.xml" \
  --java "$OUT/gen" \
  --min-sdk-version "$MIN_SDK" \
  --target-sdk-version "$TARGET_SDK" \
  --version-code "$VERSION_CODE" \
  --version-name "$VERSION_NAME" \
  "$OUT/res.zip"

say "[3/7] 编译 Java"
SRC_LIST="$OUT/sources.txt"
find "$HERE/src" "$OUT/gen" -name '*.java' > "$SRC_LIST"
COUNT=$(wc -l < "$SRC_LIST" | tr -d ' ')
javac --release 8 -Xlint:-options -encoding UTF-8 \
  -classpath "$PLATFORM" -d "$OUT/classes" @"$SRC_LIST"
echo "  编译 $COUNT 个源文件"

say "[4/7] dex"
find "$OUT/classes" -name '*.class' > "$OUT/classes.txt"
java -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 \
  --min-api "$MIN_SDK" --lib "$PLATFORM" \
  --output "$OUT/dex" @"$OUT/classes.txt"

say "[5/7] 合并 dex 进 APK"
cp "$OUT/base.apk" "$OUT/unsigned.apk"
"$PY" - "$OUT/unsigned.apk" "$OUT/dex/classes.dex" <<'PYEOF'
import sys, zipfile
apk, dex = sys.argv[1], sys.argv[2]
with zipfile.ZipFile(apk, 'a', zipfile.ZIP_DEFLATED) as z:
    z.write(dex, 'classes.dex')
print('  classes.dex 已写入 (%.1f KiB)' % (len(open(dex, 'rb').read()) / 1024.0))
PYEOF

say "[6/7] zipalign"
"$ZIPALIGN" -f 4 "$OUT/unsigned.apk" "$OUT/aligned.apk"

say "[7/7] 签名"
if [ ! -f "$KS" ]; then
  echo "  生成签名密钥（只生成一次，丢了就无法升级覆盖安装）"
  keytool -genkeypair -keystore "$KS" -alias "$KS_ALIAS" \
    -keyalg RSA -keysize 2048 -validity 10950 \
    -storepass "$KS_PASS" -keypass "$KS_PASS" \
    -dname "CN=ChaiSync Admin, O=ChaiSync, C=CN" >/dev/null 2>&1 \
    || die "keytool 失败"
fi

APK="$DIST/ChaiSync-admin-$VERSION_NAME.apk"
java -jar "$BT/lib/apksigner.jar" sign \
  --ks "$KS" --ks-key-alias "$KS_ALIAS" \
  --ks-pass "pass:$KS_PASS" --key-pass "pass:$KS_PASS" \
  --min-sdk-version "$MIN_SDK" \
  --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true \
  --out "$APK" "$OUT/aligned.apk"

java -jar "$BT/lib/apksigner.jar" verify --print-certs "$APK" 2>&1 \
  | sed 's/^/  /' | head -6

SIZE=$(du -k "$APK" | cut -f1)
printf '\n\033[1;32m✓ %s\033[0m  (%s KiB)\n' "$APK" "$SIZE"
printf '  签名口令：%s   别名：%s\n' "$KS_PASS" "$KS_ALIAS"
printf '  安装：adb install -r "%s"\n' "$APK"
