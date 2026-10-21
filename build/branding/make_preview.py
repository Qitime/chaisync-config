# -*- coding: utf-8 -*-
"""把图标丢进一个模拟的 TV 桌面网格里看效果（含圆形遮罩检查）。"""
import os
from PIL import Image, ImageDraw

HERE = os.path.dirname(os.path.abspath(__file__))
icon = Image.open(os.path.join(HERE, "xemby_icon_1024.png")).convert("RGBA")

# 1) 圆形遮罩检查
circ = Image.new("L", (1024, 1024), 0)
ImageDraw.Draw(circ).ellipse([0, 0, 1023, 1023], fill=255)
c = Image.new("RGBA", (1024, 1024), (0, 0, 0, 0))
c.paste(icon, (0, 0), circ)
c.resize((200, 200), Image.LANCZOS).save(os.path.join(HERE, "preview_circle.png"))

# 2) 模拟 TV 网格：深灰底 + 若干占位图标 + 我们的图标
W, H = 960, 540
bg = Image.new("RGB", (W, H), (26, 27, 30))
d = ImageDraw.Draw(bg)
# 渐变光
for y in range(H):
    d.line([(0, y), (W, y)], fill=(26 + int(6 * (1 - y / H)), 27 + int(6 * (1 - y / H)), 31 + int(6 * (1 - y / H))))

tiles = [
    ("#E0533D", "A"), ("#2D7FF9", "B"), ("#F2B01E", "C"),
    ("#7B4BFF", "D"), ("#1FA97D", "E"), ("#C23B72", "F"),
]
cell, pad = 96, 34
top = 120
icon_small = icon.resize((cell, cell), Image.LANCZOS)
# 把我们的图标插到第 4 个位置（第二行第二个）
slots = [(i, j) for j in range(2) for i in range(4)]
for k, (i, j) in enumerate(slots):
    x = 90 + i * (cell + pad)
    y = top + j * (cell + 52)
    if k == 5:
        bg.paste(icon_small, (x, y), icon_small)
        # 焦点框
        d.rounded_rectangle([x - 9, y - 9, x + cell + 9, y + cell + 9], radius=18,
                            outline=(255, 255, 255), width=3)
    else:
        col = tiles[k % len(tiles)][0]
        d.rounded_rectangle([x, y, x + cell, y + cell], radius=22, fill=col)
        d.ellipse([x + 32, y + 30, x + 64, y + 62], fill=(255, 255, 255, 60))
bg.save(os.path.join(HERE, "preview_launcher.png"))
print("ok")
