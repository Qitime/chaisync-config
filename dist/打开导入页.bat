@echo off
setlocal
title ChaiSync - bootstrap
cd /d "%~dp0"

if not exist "bootstrap.json" goto :missing_json
if not exist "bootstrap.html" goto :missing_html

rem bootstrap.json is pure ASCII (base64 / hex / PEM), so clip keeps it byte-exact.
type "bootstrap.json" | clip

echo.
echo   [OK] Full JSON is now in your clipboard.
echo        Paste it into WeChat "File Transfer" and send to yourself.
echo.
echo   Opening the page... (the Copy button there works too)
echo.
timeout /t 3 >nul

rem Random query forces the browser to reload instead of reusing a stale tab.
start "" "bootstrap.html?v=%RANDOM%%RANDOM%"
exit /b 0

:missing_json
echo.
echo   [x] bootstrap.json not found in:
echo       %~dp0
echo.
echo   Generate it first:
echo       node tools\bootstrap.mjs --repo owner/name --keys keys-prod --out dist\bootstrap.json
echo.
pause
exit /b 1

:missing_html
echo.
echo   [x] bootstrap.html not found.
echo       Regenerate it with:
echo       node tools\make-bootstrap-page.mjs
echo.
pause
exit /b 1
