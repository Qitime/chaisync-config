#!/usr/bin/env python3
"""APK 可逆向性量化评估。

回答"这个包能不能改"，而不是"它安不安全"。核心指标：
  1) 加固/字符串加密痕迹
  2) 反编译完整度（应用类缺失率、方法失败率）
  3) 类合并强度（最大应用合并类的方法数/体积）
  4) 包结构破坏程度 + 业务代码 import 污染率
  5) 源文件 trace 分散度（定位 UI 的难度）

用法：
  python reverse_audit.py --old A.apk --new B.apk \
      --old-src jadx_old/sources --new-src jadx_new/sources

只依赖标准库。jadx 产物用 `jadx -d out --no-res` 生成。
"""

import argparse
import collections
import os
import re
import struct
import zipfile

DEX_MAGIC = b"dex\n"
U = "<I"
APP_PKG = "com.dh.myembyapp"
APP_PATH = APP_PKG.replace(".", "/") + "/"
APP_TRACE = re.compile(r"(com\.dh\.myembyapp\.[\w.$]+) \(([A-Za-z0-9_]+\.kt):(\d+)\)")
Obf_IMPORT = re.compile(r"^(defpackage|[a-z][0-9]?)\.")


def uleb(d, p):
    r = s = 0
    while True:
        b = d[p]
        p += 1
        r |= (b & 0x7F) << s
        if not b & 0x80:
            return r, p
        s += 7


def dex_files(z):
    return sorted(
        n for n in z.namelist()
        if n.startswith("classes") and n.endswith(".dex")
        and z.read(n)[:4] == DEX_MAGIC
    )


def dex_stats(apk):
    z = zipfile.ZipFile(apk)
    out = {
        "classes": 0, "methods": 0, "with_debug": 0,
        "strings": 0, "cjk": 0, "long_b64": 0,
        "app_classes": 0, "top": collections.Counter(),
        "short_pkg": 0, "default_pkg": 0, "kotlin_metadata": 0,
        "dex": [], "app_names": set(),
    }
    for n in dex_files(z):
        d = z.read(n)
        str_sz, str_off = struct.unpack_from("<II", d, 56)
        typ_sz, typ_off = struct.unpack_from("<II", d, 64)
        cls_sz, cls_off = struct.unpack_from("<II", d, 96)
        out["dex"].append((n, len(d), cls_sz, str_sz))

        def s(i):
            o = struct.unpack_from(U, d, str_off + i * 4)[0]
            _, p = uleb(d, o)
            e = d.index(b"\x00", p)
            return d[p:e].decode("utf-8", "replace")

        for i in range(str_sz):
            v = s(i)
            out["strings"] += 1
            if re.search(r"[\u4e00-\u9fff]", v):
                out["cjk"] += 1
            if len(v) >= 40 and re.fullmatch(r"[A-Za-z0-9+/=]+", v):
                out["long_b64"] += 1

        types = [s(struct.unpack_from(U, d, typ_off + i * 4)[0]) for i in range(typ_sz)]
        out["kotlin_metadata"] += types.count("Lkotlin/Metadata;")

        for i in range(cls_sz):
            ti = struct.unpack_from(U, d, cls_off + i * 32)[0]
            desc = types[ti][1:-1]
            out["classes"] += 1
            pkg = desc.split("/")[0] if "/" in desc else ""
            out["top"][pkg or "(default)"] += 1
            if not pkg:
                out["default_pkg"] += 1
            elif len(pkg) <= 2 and re.fullmatch(r"[a-z][0-9]?", pkg):
                out["short_pkg"] += 1
            if desc.startswith(APP_PATH):
                out["app_classes"] += 1
                out["app_names"].add(desc)

            cdo = struct.unpack_from(U, d, cls_off + i * 32 + 24)[0]
            if not cdo:
                continue
            p = cdo
            sf, p = uleb(d, p)
            inf, p = uleb(d, p)
            dm, p = uleb(d, p)
            vm, p = uleb(d, p)
            for _ in range(sf + inf):
                _, p = uleb(d, p)
                _, p = uleb(d, p)
            for cnt in (dm, vm):
                for _ in range(cnt):
                    _, p = uleb(d, p)
                    _, p = uleb(d, p)
                    co, p = uleb(d, p)
                    if co:
                        out["methods"] += 1
                        dbg = struct.unpack_from(U, d, co + 8)[0]
                        if dbg:
                            out["with_debug"] += 1
    return out


def java_files(src):
    for r, _, fs in os.walk(src):
        for f in fs:
            if f.endswith(".java"):
                yield os.path.join(r, f)


def is_app_file(path, src):
    rel = os.path.relpath(path, src).replace(os.sep, "/")
    return rel.startswith(APP_PATH)


def jadx_stats(src, app_names):
    out = {
        "files": 0, "fail_methods": 0, "fail_files": 0,
        "app_files": 0, "app_fail_methods": 0,
        "app_imports": 0, "app_obf_imports": 0,
        "largest_any": [], "largest_app": [],
        "trace_kt": collections.Counter(), "trace_files": collections.defaultdict(set),
        "missing": [],
    }
    seen_app = set()
    for p in java_files(src):
        out["files"] += 1
        try:
            size = os.path.getsize(p)
            # 超大文件通常是 jadx 失控产物，跳过内容分析
            if size > 20 * 1024 * 1024:
                out["largest_any"].append((size, p))
                continue
            t = open(p, encoding="utf-8", errors="replace").read()
        except OSError:
            continue

        n = t.count("Method not decompiled")
        app_file = is_app_file(p, src)
        rel = os.path.relpath(p, src).replace(os.sep, "/")
        if app_file:
            out["app_files"] += 1
            out["app_fail_methods"] += n
            seen_app.add(rel[:-5])
        if n:
            out["fail_methods"] += n
            out["fail_files"] += 1

        if app_file:
            imps = re.findall(r"^import\s+([\w.]+);", t, re.M)
            out["app_imports"] += len(imps)
            out["app_obf_imports"] += sum(1 for i in imps if Obf_IMPORT.match(i))

        traces = APP_TRACE.findall(t)
        for _, kt, _ in traces:
            out["trace_kt"][kt] += 1
            out["trace_files"][kt].add(rel)

        out["largest_any"].append((size, p))
        if traces or (APP_PATH in t and not app_file):
            out["largest_app"].append((size, p))

    out["largest_any"].sort(reverse=True)
    out["largest_app"].sort(reverse=True)

    for cls in app_names:
        outer = cls.split("$")[0]
        if outer not in seen_app:
            out["missing"].append(cls)
    return out


def count_methods(path):
    """粗略数一下方法定义，用于区分"类合并"和"单方法被内联展开"。"""
    n = 0
    try:
        with open(path, encoding="utf-8", errors="replace") as f:
            for line in f:
                if re.match(r"\s+(public|private|protected|static|final|abstract|"
                            r"synchronized|native)\b", line) and "(" in line:
                    n += 1
    except OSError:
        pass
    return n


def report(label, apk, src):
    d = dex_stats(apk)
    j = jadx_stats(src, d["app_names"])
    miss = len(j["missing"])
    print("\n" + "=" * 68)
    print(label)
    print("=" * 68)
    print(f"  dex            {len(d['dex'])} 个，{sum(x[1] for x in d['dex'])/1048576:.2f} MB")
    print(f"  类 / 方法      {d['classes']} / {d['methods']}")
    print(f"  应用类         {d['app_classes']}（产物缺 {miss}，{miss*100.0/max(1,d['app_classes']):.1f}%）")
    print(f"  Kotlin 元数据  {'保留' if d['kotlin_metadata'] else '缺失'}")
    print(f"  调试信息       {d['with_debug']*100.0/max(1,d['methods']):.1f}%")
    print(f"  字符串         {d['strings']}（中文 {d['cjk']}，长Base64 {d['long_b64']}）")
    print(f"  包结构         短包类 {d['short_pkg']}，默认包类 {d['default_pkg']}，顶层包 {len(d['top'])}")
    print(f"  jadx 产物      {j['files']} 文件，失败方法 {j['fail_methods']}（{j['fail_files']} 文件）")
    print(f"  业务目录       {j['app_files']} 文件，失败方法 {j['app_fail_methods']}")
    print(f"  import 污染    {j['app_obf_imports']}/{j['app_imports']} = "
          f"{j['app_obf_imports']*100.0/max(1,j['app_imports']):.1f}%")
    if j["largest_app"]:
        # 按方法数找真正的"合并类"：体积大但方法少的，是单方法被内联展开
        cand = [(count_methods(p), sz, p) for sz, p in j["largest_app"][:12]]
        cand.sort(reverse=True)
        n, sz, p = cand[0]
        print(f"  方法最多合并类 {n} 方法 / {sz/1048576:.2f} MB  {os.path.relpath(p, src)}")
        sz2, p2 = j["largest_app"][0]
        print(f"  体积最大应用类 {sz2/1048576:.2f} MB / {count_methods(p2)} 方法  "
              f"{os.path.relpath(p2, src)}")
    if j["largest_any"]:
        sz, p = j["largest_any"][0]
        print(f"  最大产物       {sz/1048576:.2f} MB  {os.path.relpath(p, src)}")
    top = j["trace_kt"].most_common(3)
    if top:
        print("  trace 最多     " + "，".join(f"{k}({v})" for k, v in top))
    return d, j


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--old", required=True)
    ap.add_argument("--new", required=True)
    ap.add_argument("--old-src", required=True)
    ap.add_argument("--new-src", required=True)
    a = ap.parse_args()
    report("旧版", a.old, a.old_src)
    report("新版", a.new, a.new_src)
    print("\n判读：应用类缺失<2%、业务方法失败<1% → 代码拿得出来；")
    print("      import 污染>15% 或最大合并类>500 方法 → 改功能会明显难受。")


if __name__ == "__main__":
    main()
