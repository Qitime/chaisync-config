"""换肤/换名：把重编译得到的 AndroidManifest.xml 与品牌图替换进已成型的 APK。

只动这三类条目，其余字节原样搬运（.so 保持 STORED，交给 zipalign -p 重新对齐）。
旧的 v1 签名（META-INF/*.SF|RSA|MF）全部丢弃，签名由 apksigner 重新做。

用法：
  python zipswap.py <源apk> <重编译apk> <图标png> <banner png> <输出apk>
                    [--icon-entry=res/Y0.png] [--banner-entry=res/kK.png]
                    [--manifest=自定义AndroidManifest.xml的apk或xml]
"""
import sys
import zipfile

SIG_SUFFIXES = ('.RSA', '.DSA', '.EC', '.SF', '.MF')


def main():
    args = [a for a in sys.argv[1:] if not a.startswith('--')]
    src_apk, rebuilt_apk, icon_png, banner_png, out_apk = args[:5]

    flags = {}
    for a in sys.argv[1:]:
        if a.startswith('--'):
            k, _, v = a[2:].partition('=')
            flags[k] = v
    icon_entry = flags.get('icon-entry', 'res/Y0.png')
    banner_entry = flags.get('banner-entry', 'res/kK.png')

    # manifest 默认取重编译包里的，也可以指定另一个已编译的 apk
    manifest_apk = flags.get('manifest-apk', rebuilt_apk)
    new_manifest = zipfile.ZipFile(manifest_apk).read('AndroidManifest.xml')
    replacements = {
        'AndroidManifest.xml': new_manifest,
        icon_entry: open(icon_png, 'rb').read(),
        banner_entry: open(banner_png, 'rb').read(),
    }

    with zipfile.ZipFile(src_apk) as src, zipfile.ZipFile(out_apk, 'w') as out:
        names = set(src.namelist())
        missing = [n for n in replacements if n not in names]
        if missing:
            raise SystemExit('源 APK 里没有这些条目：%s' % missing)

        for info in src.infolist():
            name = info.filename
            if name.startswith('META-INF/') and name.upper().endswith(SIG_SUFFIXES):
                continue
            data = replacements.get(name)
            if data is None:
                data = src.read(name)
            out.writestr(info, data, compress_type=info.compress_type)

        for name, data in replacements.items():
            print('replaced %-20s %8d bytes' % (name, len(data)))

    print('->', out_apk)


if __name__ == '__main__':
    main()
