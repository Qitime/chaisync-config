# -*- coding: utf-8 -*-
"""Xemby 品牌图形生成器。

产出（全部 4x 超采样后降采样，边缘干净）：
  xemby_icon_1024.png    应用图标（替换 res/drawable/chaichai.png）
  xemby_banner_1280.png  TV banner（替换 res/drawable/chaichai_b.png）
  preview_*.png          预览图

设计取向：TV 桌面通常是深色大网格，图标要「安静但不含糊」——
深色底、单一高对比主体、无细碎描边，离屏幕两米仍可辨认。
"""
import os
import sys
from PIL import Image, ImageDraw, ImageFilter

HERE = os.path.dirname(os.path.abspath(__file__))
SS = 4  # supersample

BG_TOP = (27, 37, 49)
BG_BOT = (10, 14, 19)
GREEN_HI = (110, 219, 148)
GREEN_LO = (44, 143, 84)
ACCENT = (88, 205, 128)


def lerp(a, b, t):
    return tuple(int(round(a[i] + (b[i] - a[i]) * t)) for i in range(3))


def vgrad(size, top, bot):
    w, h = size
    img = Image.new("RGB", (w, h))
    d = ImageDraw.Draw(img)
    for y in range(h):
        d.line([(0, y), (w, y)], fill=lerp(top, bot, y / max(1, h - 1)))
    return img


def radial_glow(size, center, radius, color, peak):
    """硬画同心圆做径向渐变，中心不透明、边缘 0。"""
    w, h = size
    layer = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(layer)
    steps = 220
    for i in range(steps, 0, -1):
        t = i / steps
        r = radius * t
        a = int(peak * (1 - t) ** 2)
        if a <= 0:
            continue
        d.ellipse([center[0] - r, center[1] - r, center[0] + r, center[1] + r],
                  fill=(color[0], color[1], color[2], a))
    return layer.filter(ImageFilter.GaussianBlur(radius * 0.06))


def rounded_mask(size, radius):
    m = Image.new("L", size, 0)
    ImageDraw.Draw(m).rounded_rectangle([0, 0, size[0] - 1, size[1] - 1],
                                        radius=radius, fill=255)
    return m


def x_mask(size, span, thickness, center=None, gap_radius=0):
    """两条 45° 圆头横杠组成 X。span 是对角线长度。"""
    w, h = size
    cx, cy = center or (w / 2, h / 2)
    L = int(span)
    T = int(thickness)
    layer = Image.new("L", (int(w), int(h)), 0)
    for ang in (45, -45):
        bar = Image.new("L", (L + T, T), 0)
        ImageDraw.Draw(bar).rounded_rectangle([0, 0, bar.width - 1, bar.height - 1],
                                             radius=T / 2, fill=255)
        rot = bar.rotate(ang, resample=Image.BICUBIC, expand=True)
        layer.paste(255, (int(cx - rot.width / 2), int(cy - rot.height / 2)), rot)
    if gap_radius:
        cut = Image.new("L", (w, h), 0)
        ImageDraw.Draw(cut).ellipse([cx - gap_radius, cy - gap_radius,
                                     cx + gap_radius, cy + gap_radius], fill=255)
        layer = Image.composite(Image.new("L", (w, h), 0), layer, cut)
    return layer


def gradient_fill(size, mask, c_hi, c_lo, angle="diag"):
    w, h = size
    g = Image.new("RGB", (w, h))
    d = ImageDraw.Draw(g)
    if angle == "diag":
        for i in range(w + h):
            t = i / (w + h - 1)
            d.line([(i, 0), (0, i)], fill=lerp(c_hi, c_lo, t))
        # 上面这种斜线填充只覆盖左上三角，改成逐行更稳
        g = vgrad(size, c_hi, c_lo)
    else:
        g = vgrad(size, c_hi, c_lo)
    out = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    out.paste(g, (0, 0), mask)
    return out


def make_icon(px=1024, variant="play", span_r=0.615, thick_r=0.150, gap_r=0.108):
    S = px * SS
    # 背景
    bg = vgrad((S, S), BG_TOP, BG_BOT).convert("RGBA")
    bg = Image.alpha_composite(bg, radial_glow((S, S), (S * 0.5, S * 0.42), S * 0.62,
                                               (70, 190, 110), 34))
    # 顶部一层很淡的高光，制造玻璃感
    sheen = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    ds = ImageDraw.Draw(sheen)
    for y in range(int(S * 0.55)):
        a = int(16 * (1 - y / (S * 0.55)) ** 2)
        ds.line([(0, y), (S, y)], fill=(255, 255, 255, a))
    bg = Image.alpha_composite(bg, sheen)

    span = S * span_r
    thick = S * thick_r
    gap = S * gap_r if variant == "play" else 0
    m = x_mask((S, S), span, thick, gap_radius=gap)

    # 阴影：让标记从底上浮起来一点
    shadow = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    sh = m.filter(ImageFilter.GaussianBlur(S * 0.022))
    shadow.paste((0, 0, 0, 110), (0, 0), sh)
    shadow = shadow.transform(shadow.size, Image.AFFINE, (1, 0, 0, 0, 1, -S * 0.012))
    bg = Image.alpha_composite(bg, shadow)

    mark = gradient_fill((S, S), m, GREEN_HI, GREEN_LO)
    bg = Image.alpha_composite(bg, mark)

    if variant == "play":
        # 中心挖出的圆里放播放三角
        tri_h = S * 0.132
        tri_w = tri_h * 0.90
        cx, cy = S / 2, S / 2
        tri = [(cx - tri_w * 0.36, cy - tri_h / 2),
               (cx - tri_w * 0.36, cy + tri_h / 2),
               (cx + tri_w * 0.64, cy)]
        t = Image.new("RGBA", (S, S), (0, 0, 0, 0))
        ImageDraw.Draw(t).polygon(tri, fill=(240, 250, 244, 255))
        bg = Image.alpha_composite(bg, t)

    # 圆角 + 1.5px 细边（缩到 1024 后约 1.5px）
    mask = rounded_mask((S, S), S * 0.225)
    out = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    out.paste(bg, (0, 0), mask)

    border = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    bd = ImageDraw.Draw(border)
    inset = SS * 1.2
    bd.rounded_rectangle([inset, inset, S - 1 - inset, S - 1 - inset],
                         radius=S * 0.225 - inset, outline=(255, 255, 255, 26),
                         width=int(SS * 1.6))
    out = Image.alpha_composite(out, Image.composite(border, Image.new("RGBA", (S, S), (0, 0, 0, 0)), mask))

    return out.resize((px, px), Image.LANCZOS)


def make_banner(w=1280, h=720):
    S = SS
    W, H = w * S, h * S
    bg = vgrad((W, H), (24, 33, 43), (9, 12, 17)).convert("RGBA")
    bg = Image.alpha_composite(bg, radial_glow((W, H), (W * 0.13, H * 0.52), W * 0.42,
                                               (70, 190, 110), 46))
    # 右侧极淡的第二光源，避免大片死黑
    bg = Image.alpha_composite(bg, radial_glow((W, H), (W * 0.86, H * 0.16), W * 0.34,
                                               (80, 150, 255), 12))

    # 左侧 X 标记
    mark_size = int(H * 0.60)
    span = mark_size * 0.615
    thick = mark_size * 0.150
    m = x_mask((mark_size, mark_size), span, thick, gap_radius=mark_size * 0.108)
    mk = gradient_fill((mark_size, mark_size), m, GREEN_HI, GREEN_LO)
    sh = Image.new("RGBA", (mark_size, mark_size), (0, 0, 0, 0))
    sh.paste((0, 0, 0, 120), (0, 0), m.filter(ImageFilter.GaussianBlur(mark_size * 0.02)))
    mx = int(W * 0.075)
    my = int(H * 0.5 - mark_size * 0.5)
    bg.alpha_composite(sh, (mx, my + int(mark_size * 0.012)))
    bg.alpha_composite(mk, (mx, my))
    # 播放三角
    tri_h = mark_size * 0.150
    tri_w = tri_h * 0.90
    tc = (mx + mark_size / 2, my + mark_size / 2)
    t = Image.new("RGBA", (W, H), (0, 0, 0, 0))
    ImageDraw.Draw(t).polygon([(tc[0] - tri_w * 0.34, tc[1] - tri_h / 2),
                               (tc[0] - tri_w * 0.34, tc[1] + tri_h / 2),
                               (tc[0] + tri_w * 0.62, tc[1])], fill=(240, 250, 244, 255))
    bg = Image.alpha_composite(bg, t)

    # 文字
    try:
        from PIL import ImageFont
        font_path = "C:/Windows/Fonts/seguisb.ttf"
        f = ImageFont.truetype(font_path, int(H * 0.30))
        small = ImageFont.truetype(font_path, int(H * 0.075))
    except Exception:
        f = small = None

    if f:
        d = ImageDraw.Draw(bg)
        tx = mx + mark_size + int(W * 0.055)
        avail = W - tx - int(W * 0.075)
        size = int(H * 0.26)
        while size > 20:
            f = ImageFont.truetype("C:/Windows/Fonts/seguisb.ttf", size)
            bbox = d.textbbox((0, 0), "Xemby", font=f)
            if bbox[2] - bbox[0] <= avail:
                break
            size -= 3
        bbox = d.textbbox((0, 0), "Xemby", font=f)
        th = bbox[3] - bbox[1]
        top = H * 0.5 - th * 0.5
        d.text((tx, top - bbox[1]), "Xemby", font=f, fill=(242, 247, 250, 255))
    return bg.convert("RGB").resize((w, h), Image.LANCZOS)


def sheet():
    """三个候选并排 + 小尺寸行，方便判断远看效果。"""
    variants = [
        ("A", dict(span_r=0.545, thick_r=0.128, gap_r=0.116)),
        ("B", dict(span_r=0.590, thick_r=0.140, gap_r=0.104)),
        ("C", dict(span_r=0.560, thick_r=0.132, gap_r=0.118)),
    ]
    cell = 240
    gap = 24
    W = len(variants) * cell + (len(variants) + 1) * gap
    H = cell + 96 + 3 * gap
    canvas = Image.new("RGB", (W, H), (32, 32, 34))
    d = ImageDraw.Draw(canvas)
    for i, (name, kw) in enumerate(variants):
        im = make_icon(560, "play", **kw)
        x = gap + i * (cell + gap)
        canvas.paste(im.resize((cell, cell), Image.LANCZOS).convert("RGB"), (x, gap))
        d.text((x + 4, gap + cell + 12), name, fill=(220, 220, 220))
        sm = im.resize((72, 72), Image.LANCZOS).convert("RGB")
        canvas.paste(sm, (x + 4, gap + cell + 34))
        sm2 = im.resize((48, 48), Image.LANCZOS).convert("RGB")
        canvas.paste(sm2, (x + 92, gap + cell + 46))
    canvas.save(os.path.join(HERE, "preview_sheet.png"))


def main():
    out = HERE
    icon = make_icon(1024, "play")
    icon.save(os.path.join(out, "xemby_icon_1024.png"))
    icon.resize((256, 256), Image.LANCZOS).save(os.path.join(out, "preview_icon_256.png"))
    icon.resize((120, 120), Image.LANCZOS).save(os.path.join(out, "preview_icon_120.png"))

    v1 = make_icon(1024, "plain")
    v1.save(os.path.join(out, "alt_icon_plain.png"))

    ban = make_banner()
    ban.save(os.path.join(out, "xemby_banner_1280.png"))
    ban.resize((640, 360), Image.LANCZOS).save(os.path.join(out, "preview_banner_640.png"))
    sheet()
    print("ok")


if __name__ == "__main__":
    main()
