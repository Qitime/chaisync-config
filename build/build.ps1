param(
    [Parameter(Mandatory = $true)][string]$MasterB64,
    [Parameter(Mandatory = $true)][string]$PubKeyHex,
    [Parameter(Mandatory = $true)][string]$RawBase,
    [string]$Mirrors   = "https://ghfast.top/,https://gh-proxy.com/,https://ghproxy.net/",
    [string]$Apk       = "C:\Users\qiqi\Downloads\ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk",
    [switch]$SkipInstall
)

# Repack ChaiChai Emby TV with the GitHub-mirror sync provider.
#
# Design notes kept out of the code on purpose:
#   - only AndroidManifest.xml + assets/sync_config.json are replaced, plus one added classes6.dex,
#     everything else is copied byte-for-byte (the .so files stay uncompressed and page aligned)
#   - the two secrets arrive as parameters and never touch the source tree
#
# Usage:
#   powershell -ExecutionPolicy Bypass -File .\build\build.ps1 `
#     -MasterB64 "<base64>" -PubKeyHex "<hex>" `
#     -RawBase "https://raw.githubusercontent.com/you/repo/main/dist/config.enc.json"

$ErrorActionPreference = 'Stop'

$root      = Split-Path -Parent $PSScriptRoot
$work      = Join-Path $root 'build'
$srcDir    = Join-Path $root 'app\src'
$assetPath = Join-Path $root 'app\assets\sync_config.json'
$apktool   = "D:\Ai\tools\apkrev-tools\apktool\apktool.jar"
$tools     = "D:\Ai\Android\sdk\build-tools\37.0.0"
$androidJar = "D:\Ai\Android\sdk\platforms\android-36\android.jar"
$adb       = "D:\Tv\QtScrcpy-win-x64-v3.3.3\adb.exe"
$ks        = Join-Path $work 'xemby.keystore'
$brandIcon   = Join-Path $work 'branding\xemby_icon_1024.png'
$brandBanner = Join-Path $work 'branding\xemby_banner_1280.png'

New-Item -ItemType Directory -Force -Path $work | Out-Null
New-Item -ItemType Directory -Force -Path (Join-Path $work 'classes') | Out-Null
New-Item -ItemType Directory -Force -Path (Join-Path $work 'dexout') | Out-Null

Write-Host "[1/7] writing assets/sync_config.json"
$cfg = [ordered]@{
    enabled       = $true
    rawUrl        = $RawBase
    pubKeyHex     = $PubKeyHex
    masterB64     = $MasterB64
    mirrors       = @($Mirrors.Split(',') | ForEach-Object { $_.Trim() } | Where-Object { $_ -ne '' })
    probeTcpMs    = 1200
    probeHttpMs   = 1500
    fetchBudgetMs = 9000
    fetchTryMs    = 3200
}
$json = $cfg | ConvertTo-Json -Depth 5
[System.IO.File]::WriteAllText($assetPath, $json, (New-Object System.Text.UTF8Encoding($false)))

$decodedPath = Join-Path $work 'decoded'
$patchedApk  = Join-Path $work 'patched.apk'
if (-not (Test-Path $decodedPath)) {
    Write-Host "[2/7] apktool d -s (manifest + resources only, skips smali)"
    & java -jar $apktool d -f -s -o $decodedPath $Apk | Out-Null
    if ($LASTEXITCODE -ne 0) { throw 'apktool d failed' }
}

$manifest = Join-Path $decodedPath 'AndroidManifest.xml'
$txt = Get-Content $manifest -Raw -Encoding UTF8
$mChanged = $false
if ($txt -notmatch 'com\.dh\.myembyapp\.sync\.SyncProvider') {
    $provider = '    <provider android:name="com.dh.myembyapp.sync.SyncProvider" android:authorities="com.dh.myembyapp.sync.provider" android:exported="false" android:initOrder="1000" />' + "`n"
    $txt = $txt.Replace('</application>', $provider + '</application>')
    $mChanged = $true
}
# 应用名改成字面量：只动 manifest，不碰 resources.arsc
if ($txt.Contains('android:label="@string/app_name"')) {
    $txt = $txt.Replace('android:label="@string/app_name"', 'android:label="Xemby"')
    $mChanged = $true
}
if ($mChanged) {
    [System.IO.File]::WriteAllText($manifest, $txt, (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "[2/7] manifest patched (provider / label=Xemby)"
} else {
    Write-Host "[2/7] manifest already patched"
}

Write-Host "[3/7] apktool b"
& java -jar $apktool b -f -o $patchedApk $decodedPath | Out-Null
if ($LASTEXITCODE -ne 0) { throw 'apktool b failed' }

Write-Host "[4/7] javac + d8"
$srcs = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }
& javac --release 8 -Xlint:-options -encoding UTF-8 -classpath $androidJar -d (Join-Path $work 'classes') $srcs
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }
& jar cf (Join-Path $work 'patch.jar') -C (Join-Path $work 'classes') .
& (Join-Path $tools 'd8.bat') --min-api 23 --lib $androidJar --output (Join-Path $work 'dexout') (Join-Path $work 'patch.jar') | Out-Null
if ($LASTEXITCODE -ne 0) { throw 'd8 failed' }

Write-Host "[5/7] splice + brand + zipalign"
$dex = Join-Path $work 'dexout\classes.dex'
if (-not (Test-Path $dex)) { throw 'd8 produced no classes.dex' }
& python (Join-Path $PSScriptRoot 'splice.py') $Apk $patchedApk $dex $assetPath (Join-Path $work 'final-unaligned.apk')
if ($LASTEXITCODE -ne 0) { throw 'splice failed' }
if ((Test-Path $brandIcon) -and (Test-Path $brandBanner)) {
    & python (Join-Path $PSScriptRoot 'zipswap.py') (Join-Path $work 'final-unaligned.apk') $patchedApk `
        $brandIcon $brandBanner (Join-Path $work 'final-branded.apk')
    if ($LASTEXITCODE -ne 0) { throw 'brand swap failed' }
    Move-Item -Force (Join-Path $work 'final-branded.apk') (Join-Path $work 'final-unaligned.apk')
}
& (Join-Path $tools 'zipalign.exe') -p -f 4 (Join-Path $work 'final-unaligned.apk') (Join-Path $work 'final-aligned.apk')
if ($LASTEXITCODE -ne 0) { throw 'zipalign failed' }

Write-Host "[6/7] apksigner"
if (-not (Test-Path $ks)) {
    & keytool -genkeypair -keystore $ks -alias xemby -keyalg RSA -keysize 2048 -validity 10000 `
        -storepass xemby2026 -keypass xemby2026 `
        -dname "CN=Xemby, OU=Dev, O=Xemby, L=City, ST=State, C=CN" | Out-Null
}
$outApk = Join-Path $work 'Xemby-tv.apk'
& (Join-Path $tools 'apksigner.bat') sign --ks $ks --ks-key-alias xemby `
    --ks-pass pass:xemby2026 --key-pass pass:xemby2026 `
    --out $outApk (Join-Path $work 'final-aligned.apk')
if ($LASTEXITCODE -ne 0) { throw 'apksigner failed' }

Write-Host "[7/7] OK -> $outApk"

if (-not $SkipInstall) {
    & $adb uninstall com.dh.myembyapp | Out-Null
    & $adb install $outApk
    Write-Host ""
    Write-Host "installed. open the app once, then read the trace:"
    Write-Host "  adb shell cat /sdcard/Android/data/com.dh.myembyapp/files/sync_status.txt"
}
