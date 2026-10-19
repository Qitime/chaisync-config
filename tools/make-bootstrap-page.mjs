#!/usr/bin/env node
/**
 * 把 dist/bootstrap.json 变成一张自包含的网页，用来"一键复制"。
 *
 * 为什么需要它：bootstrap 是一行 876 字节的 JSON，微信里发 .json 文件
 * 手机上经常没有合适的打开方式，也没法整段选中。HTML 能自带复制按钮。
 *
 * 用法：
 *   node tools/make-bootstrap-page.mjs
 *   node tools/make-bootstrap-page.mjs --in dist/bootstrap.json --out dist/bootstrap.html
 *
 * 产物含发布私钥，.gitignore 已排除，别外传。
 */
import { readFileSync, writeFileSync, existsSync } from 'node:fs';
import { join, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';
import crypto from 'node:crypto';

const here = dirname(fileURLToPath(import.meta.url));
const root = join(here, '..');

function arg(name, dflt) {
  const i = process.argv.indexOf('--' + name);
  return i > 0 && process.argv[i + 1] ? process.argv[i + 1] : dflt;
}

const srcPath = arg('in', join(root, 'dist', 'bootstrap.json'));
const outPath = arg('out', join(root, 'dist', 'bootstrap.html'));

if (!existsSync(srcPath)) {
  console.error('✗ 找不到 ' + srcPath);
  console.error('  先生成：node tools/bootstrap.mjs --repo owner/name --keys keys-prod --out dist/bootstrap.json');
  process.exit(2);
}

const raw = readFileSync(srcPath, 'utf8').trim();

let obj;
try {
  obj = JSON.parse(raw);
} catch (e) {
  console.error('✗ ' + srcPath + ' 不是合法 JSON：' + e.message);
  process.exit(2);
}

// 公钥指纹：与 bootstrap.mjs、Android 端 EnvelopeSeal.fingerprint() 同一算法
let fp = '—';
if (typeof obj.pubKeyHex === 'string' && /^[0-9a-fA-F]+$/.test(obj.pubKeyHex)) {
  fp = crypto.createHash('sha256')
    .update(Buffer.from(obj.pubKeyHex, 'hex'))
    .digest('hex').slice(0, 12).toUpperCase()
    .match(/../g).join(':');
}

const bytes = Buffer.byteLength(raw, 'utf8');
const fieldCount = Object.keys(obj).length;
const repo = obj.repo || '—';
const branch = obj.branch || '—';
const targetPath = obj.path || '—';
const tokenTail = typeof obj.token === 'string' && obj.token.length > 8
  ? obj.token.slice(0, 4) + '…' + obj.token.slice(-4)
  : '—';

// 塞进 <script> 时把 < 转义掉，避免正文里出现 </script> 提前闭合
const rawJs = JSON.stringify(raw).replace(/</g, '\\u003c');

const fieldRows = [
  ['v', '格式版本'],
  ['repo', 'GitHub 仓库 owner/name'],
  ['branch', '分支'],
  ['path', '信封在仓库里的路径'],
  ['apiBase', 'Contents API 基址（可换反代）'],
  ['token', 'PAT，需要 Contents: Read and write'],
  ['kid', '密钥标识，KEK = HKDF(master, kid)'],
  ['masterB64', '主密钥，用于解密信封'],
  ['signingPem', '发布私钥（ECDSA P-256），<b>必须保密</b>'],
  ['pubKeyHex', '公钥，导入时用来自检密钥对'],
  ['rawUrl', '电视端拉取的原始地址'],
  ['mirrors', '加速镜像列表，按顺序重试'],
  ['seq', '起始序号（可选，留空则发布时读远端）'],
];

const html = `<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ChaiSync · 手机导入串</title>
<style>
  :root{
    --bg:#eef1f6; --card:#ffffff; --ink:#141b26; --ink2:#4a5568; --ink3:#7b8794;
    --line:#e3e8ef; --accent:#2e3d60; --accent2:#4f8cff; --ok:#0f7b4f;
    --warn-bg:#fff8e6; --warn-line:#f0d9a8; --warn-ink:#7a5a14;
    --mono:ui-monospace,SFMono-Regular,"SF Mono",Consolas,"Liberation Mono",Menlo,monospace;
  }
  *{box-sizing:border-box}
  html,body{margin:0}
  body{
    padding:44px 20px 72px;
    background:radial-gradient(1100px 520px at 50% -220px,#dbe4f3 0%,var(--bg) 62%);
    color:var(--ink);
    font-family:-apple-system,BlinkMacSystemFont,"Segoe UI","Microsoft YaHei","PingFang SC",sans-serif;
    -webkit-font-smoothing:antialiased;
  }
  .wrap{max-width:800px;margin:0 auto}
  .eyebrow{
    display:inline-block;font-size:12px;letter-spacing:.12em;text-transform:uppercase;
    color:var(--ink3);font-weight:600;margin-bottom:10px;
  }
  h1{font-size:26px;line-height:1.25;margin:0 0 10px;letter-spacing:-.01em}
  .sub{margin:0 0 26px;color:var(--ink2);font-size:14.5px;line-height:1.65}
  .sub code{
    font-family:var(--mono);font-size:13px;background:#e9eef7;color:var(--accent);
    padding:1px 5px;border-radius:4px;
  }
  .card{
    background:var(--card);border:1px solid var(--line);border-radius:16px;
    box-shadow:0 1px 2px rgba(20,27,38,.04),0 12px 32px -18px rgba(20,27,38,.18);
    padding:22px;
  }
  .warn{
    display:flex;gap:10px;align-items:flex-start;
    background:var(--warn-bg);border:1px solid var(--warn-line);color:var(--warn-ink);
    border-radius:10px;padding:12px 14px;font-size:13px;line-height:1.6;margin-bottom:20px;
  }
  .warn b{color:#5c4209}
  .warn .dot{
    flex:0 0 auto;width:18px;height:18px;border-radius:50%;background:var(--warn-line);
    color:#6b4d0c;font-size:12px;font-weight:700;display:flex;align-items:center;
    justify-content:center;margin-top:1px;
  }
  .bar{display:flex;align-items:center;gap:14px;margin-bottom:14px;flex-wrap:wrap}
  button{
    font:inherit;font-size:15px;font-weight:600;letter-spacing:.01em;
    color:#fff;background:linear-gradient(180deg,#3b4d75,var(--accent));
    border:0;border-radius:10px;padding:12px 24px;cursor:pointer;
    box-shadow:0 1px 2px rgba(20,27,38,.2),inset 0 1px 0 rgba(255,255,255,.14);
    transition:transform .08s ease,box-shadow .16s ease,background .16s ease;
  }
  button:hover{box-shadow:0 3px 14px -2px rgba(46,61,96,.42),inset 0 1px 0 rgba(255,255,255,.14)}
  button:active{transform:translateY(1px)}
  button.done{background:linear-gradient(180deg,#13935e,var(--ok));cursor:default}
  button:focus-visible{outline:2px solid var(--accent2);outline-offset:2px}
  .hint{font-size:13px;color:var(--ink3);min-height:18px}
  .hint.ok{color:var(--ok);font-weight:600}
  textarea{
    display:block;width:100%;height:260px;min-height:170px;max-height:460px;
    resize:vertical;overflow:auto;
    font-family:var(--mono);font-size:12.5px;line-height:1.7;
    color:#1f2937;background:#fbfcfe;
    border:1px solid var(--line);border-radius:10px;
    padding:14px;outline:none;word-break:break-all;white-space:pre-wrap;
  }
  textarea:focus{border-color:#b9c7e0;box-shadow:0 0 0 3px rgba(79,140,255,.12)}
  .meta{
    display:flex;gap:8px;flex-wrap:wrap;align-items:center;
    margin-top:14px;font-size:12.5px;color:var(--ink3);
  }
  .meta span.sep{color:#a9b4c4}
  .meta b{color:var(--ink2);font-weight:600}
  .fp{font-family:var(--mono);letter-spacing:.02em}
  details{margin-top:22px;border-top:1px solid var(--line);padding-top:16px}
  summary{cursor:pointer;font-size:13.5px;font-weight:600;color:var(--ink2);list-style:none}
  summary::-webkit-details-marker{display:none}
  summary::before{content:"▸ ";color:var(--ink3)}
  details[open] summary::before{content:"▾ "}
  table{width:100%;border-collapse:collapse;margin-top:14px;font-size:13px}
  td{padding:7px 10px;border-bottom:1px solid #f0f3f8;vertical-align:top;line-height:1.55}
  td:first-child{
    width:132px;font-family:var(--mono);font-size:12px;color:var(--accent);
    white-space:nowrap;
  }
  td:last-child{color:var(--ink2)}
  .steps{margin:14px 0 0;padding-left:20px;font-size:13.5px;line-height:1.9;color:var(--ink2)}
  .steps b{color:var(--ink)}
  .foot{margin-top:26px;font-size:12px;color:var(--ink3);line-height:1.7}
  @media (max-width:560px){
    body{padding:28px 14px 48px}
    h1{font-size:22px}
    .card{padding:16px}
    textarea{height:240px}
    td:first-child{width:96px}
  }
</style>
</head>
<body>
<div class="wrap">
  <div class="eyebrow">ChaiSync Admin · 一次性导入</div>
  <h1>手机端导入串</h1>
  <p class="sub">
    点下面的按钮把整段复制走，发到微信「文件传输助手」，在手机上长按粘贴进 App 的导入框。
    粘完这条消息就可以删了。
  </p>

  <div class="card">
    <div class="warn">
      <div class="dot">!</div>
      <div>
        <b>这段内容含 GitHub token 和发布私钥。</b>
        别发群、别截图外传、别存网盘。谁拿到它，谁就能给你的电视推送配置。
      </div>
    </div>

    <div class="bar">
      <button id="copy" type="button">复制全部</button>
      <span class="hint" id="hint"></span>
    </div>

    <textarea id="ta" readonly spellcheck="false" aria-label="bootstrap JSON"></textarea>

    <div class="meta">
      <span><b>${bytes}</b> 字节</span>
      <span class="sep">·</span>
      <span><b>${fieldCount}</b> 个字段</span>
      <span class="sep">·</span>
      <span class="fp">${repo} @ ${branch}</span>
      <span class="sep">·</span>
      <span class="fp">${targetPath}</span>
      <span class="sep">·</span>
      <span>token <b class="fp">${tokenTail}</b></span>
    </div>

    <details>
      <summary>这串东西里有什么</summary>
      <table>
        ${fieldRows.map(([k, v]) => `<tr><td>${k}</td><td>${v}</td></tr>`).join('\n        ')}
      </table>
      <p class="steps">
        <b>导入之后</b>，App 会把这串东西锁进 AndroidKeyStore 保护的存储里，
        以后每次发布只读本地，不用再碰它。
      </p>
    </details>

    <details>
      <summary>公钥指纹与电视端对照</summary>
      <p class="steps">
        这串里的公钥指纹是 <b class="fp">${fp}</b>。<br>
        电视端 APK 里编进去的是同一把公钥。想核对的话，看手机 App 导入成功后显示的指纹是否与这里一致。
      </p>
    </details>
  </div>

  <p class="foot">
    这个页面是本地文件，不联网、不上传，内容全部内嵌在 HTML 里。<br>
    用完之后删掉它，或者至少别放在会被同步 / 备份的目录里。
  </p>
</div>

<script>
(function () {
  var RAW = ${rawJs};
  var ta = document.getElementById('ta');
  var btn = document.getElementById('copy');
  var hint = document.getElementById('hint');
  var timer = null;

  ta.value = RAW;

  function flash(ok) {
    if (timer) clearTimeout(timer);
    if (ok) {
      btn.textContent = '已复制 ✓';
      btn.classList.add('done');
      hint.textContent = '现在去微信粘贴。';
      hint.className = 'hint ok';
    } else {
      hint.textContent = '自动复制被浏览器拦了 —— 文本框里已全选，按 Ctrl+C。';
      hint.className = 'hint';
    }
    timer = setTimeout(function () {
      btn.textContent = '复制全部';
      btn.classList.remove('done');
      hint.textContent = '';
      hint.className = 'hint';
    }, ok ? 2600 : 6000);
  }

  function selectAll() {
    ta.focus();
    ta.select();
    try { ta.setSelectionRange(0, ta.value.length); } catch (e) {}
  }

  function unselect() {
    try { ta.setSelectionRange(0, 0); } catch (e) {}
    ta.blur();
  }

  // 按内容撑高，避免固定 320px 在 875 字节时留一大片空白
  function autosize() {
    ta.style.height = 'auto';
    var h = ta.scrollHeight + 2;
    ta.style.height = Math.min(Math.max(h, 170), 460) + 'px';
  }

  btn.addEventListener('click', function () {
    // file:// 下 execCommand 最可靠（同步），clipboard API 作为后备
    selectAll();
    var ok = false;
    try { ok = document.execCommand('copy'); } catch (e) { ok = false; }

    if (ok) { unselect(); flash(true); return; }

    if (navigator.clipboard && navigator.clipboard.writeText) {
      navigator.clipboard.writeText(RAW).then(
        function () { unselect(); flash(true); },
        function () { flash(false); }
      );
      return;
    }
    flash(false);
  });

  // 打开时只撑高度，不自动全选 —— 满屏蓝色反而看不清内容
  autosize();
  window.addEventListener('resize', autosize);
})();
</script>
</body>
</html>
`;

writeFileSync(outPath, html, 'utf8');

console.log('');
console.log('✓ 已生成 ' + outPath);
console.log(`  源文件    ${srcPath}（${bytes} 字节）`);
console.log(`  仓库      ${repo} @ ${branch}`);
console.log(`  公钥指纹  ${fp}`);
console.log('');
console.log('  双击 dist\\\\打开导入页.bat 即可复制并查看。');
