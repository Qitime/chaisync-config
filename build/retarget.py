"""把母 APK 的包名整体换掉，做成能和老版并存的「第二份应用」。

关键区分：
  * package= 与 authority / 自定义权限名 —— 必须换，否则同名同权会撞车
  * 组件类名（MainActivity / DlnaService / SyncProvider …）—— **绝不能换**，
    它们在 dex 里就是 com.dh.myembyapp.* 的全限定名，改了等于指向不存在的类

用法：
  python retarget.py <AndroidManifest.xml> com.xemby.tv
"""
import re
import sys

# 跟着包名走的后缀（authority / 签名级权限名）。组件类名不在此列。
RETARGET_SUFFIXES = (
    '.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION',
    '.sync.provider',
    '.fileprovider',
    '.androidx-startup',
)


def main():
    path, new_pkg = sys.argv[1], sys.argv[2]
    text = open(path, encoding='utf-8').read()

    m = re.search(r'package="([A-Za-z0-9_.]+)"', text)
    if not m:
        raise SystemExit('找不到 package 属性')
    old_pkg = m.group(1)
    if old_pkg == new_pkg:
        print('已是 %s，无需改动' % new_pkg)
        return

    # 先换长串（authority / 权限），最后换 package 属性本身
    for suffix in RETARGET_SUFFIXES:
        n = text.count(old_pkg + suffix)
        text = text.replace(old_pkg + suffix, new_pkg + suffix)
        if n:
            print('  %-45s x%d' % (suffix, n))

    text = text.replace('package="%s"' % old_pkg, 'package="%s"' % new_pkg, 1)
    print('  package: %s -> %s' % (old_pkg, new_pkg))

    open(path, 'w', encoding='utf-8', newline='').write(text)

    left = sorted(set(re.findall(re.escape(old_pkg) + r'[A-Za-z0-9_.]*', text)))
    if left:
        print('  仍保留旧包名前缀的（应全部是组件类名）:')
        for item in left:
            print('    ' + item)


if __name__ == '__main__':
    main()
