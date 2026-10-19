"""最小改动拼包：原 APK 全量保留，仅替换 AndroidManifest.xml / assets/sync_config.json，并追加 classes6.dex。"""
import sys
import zipfile

src_apk, rebuilt_apk, dex_file, asset_file, out_apk = sys.argv[1:6]

new_manifest = zipfile.ZipFile(rebuilt_apk).read('AndroidManifest.xml')
dex_bytes = open(dex_file, 'rb').read()
asset_bytes = open(asset_file, 'rb').read()

SIG_SUFFIXES = ('.RSA', '.DSA', '.EC', '.SF', '.MF')
ASSET_NAME = 'assets/sync_config.json'

with zipfile.ZipFile(src_apk) as src, zipfile.ZipFile(out_apk, 'w') as out:
    kept = replaced = 0
    names = set()
    for info in src.infolist():
        name = info.filename
        names.add(name)
        if name.startswith('META-INF/') and name.upper().endswith(SIG_SUFFIXES):
            continue
        data = src.read(name)
        if name == 'AndroidManifest.xml':
            data = new_manifest
            replaced += 1
        elif name == ASSET_NAME:
            data = asset_bytes
            replaced += 1
        out.writestr(info, data, compress_type=info.compress_type)
        kept += 1

    if ASSET_NAME not in names:
        info = zipfile.ZipInfo(ASSET_NAME)
        info.compress_type = zipfile.ZIP_DEFLATED
        info.external_attr = 0o644 << 16
        out.writestr(info, asset_bytes)
        print('added', ASSET_NAME)

    info = zipfile.ZipInfo('classes6.dex')
    info.compress_type = zipfile.ZIP_DEFLATED
    info.external_attr = 0o644 << 16
    out.writestr(info, dex_bytes)

print(f'entries={kept} replaced={replaced} classes6.dex={len(dex_bytes)} bytes')
