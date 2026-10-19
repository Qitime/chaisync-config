#!/usr/bin/env node
/**
 * 生成子 APK 导入用的 bootstrap JSON。
 *
 * 为什么要有这一步：子 APK 不能把发布私钥编进去（谁拆包谁就能伪造信封），
 * 所以私钥必须在首次使用时"喂"给它。手打 PEM 是不可能完成的任务，
 * 所以这里把 keys/ 里的三样东西 + 仓库参数打成一个 JSON，粘一次就完事。
 *
 * 用法：
 *   node tools/bootstrap.mjs --repo owner/name --token github_pat_xxx
 *   node tools/bootstrap.mjs --repo owner/name --token xxx --pretty
 *   node tools/bootstrap.mjs --repo owner/name --token xxx --out bootstrap.json
 *
 * token 也可以放环境变量 GH_TOKEN / GITHUB_TOKEN，避免进 shell 历史。
 *
 * 输出：JSON 走 stdout（方便直接选中复制），提示走 stderr。
 */
import { readFileSync, writeFileSync, existsSync } from 'node:fs';
import { join } from 'node:path';
import crypto from 'node:crypto';

function arg(name, dflt) {
  const i = process.argv.indexOf('--' + name);
  return i > 0 && process.argv[i + 1] ? process.argv[i + 1] : dflt;
}
function flag(name) {
  return process.argv.includes('--' + name);
}
function die(msg) {
  console.error('✗ ' + msg);
  process.exit(2);
}

const keysDir = arg('keys', 'keys');
const repo = (arg('repo', '') || '').trim();
const branch = arg('branch', 'main');
const path = arg('path', 'dist/config.enc.json');
const kid = arg('kid', 'k1');
const apiBase = arg('api-base', 'https://api.github.com');
const token = arg('token', process.env.GH_TOKEN || process.env.GITHUB_TOKEN || '');

if (!repo || !repo.includes('/')) {
  console.error('用法：node tools/bootstrap.mjs --repo owner/name --token ghp_xxx');
  console.error('参数：--branch main  --path dist/config.enc.json  --kid k1');
  console.error('      --api-base https://api.github.com  --mirror a,b,c  --pretty  --out file');
  process.exit(2);
}
if (!token) {
  die('缺少 token。用 --token 传入，或先 set GH_TOKEN=...');
}
if (!existsSync(join(keysDir, 'signing.pem'))) {
  die(`找不到 ${keysDir}/signing.pem。先跑：node tools/publish.mjs genkey --out ${keysDir}`);
}

const pem = readFileSync(join(keysDir, 'signing.pem'), 'utf8');
const masterB64 = readFileSync(join(keysDir, 'master.b64'), 'utf8').trim();
const pubKeyHex = readFileSync(join(keysDir, 'pubkey.hex'), 'utf8').trim();

// 公钥指纹：SHA-256 前 6 字节，与 Java 端 EnvelopeSeal.fingerprint() 算法一致。
// 用来人工核对"手机上导入的"和"电视里编进去的"是同一对密钥。
const fp = crypto.createHash('sha256')
  .update(Buffer.from(pubKeyHex, 'hex'))
  .digest('hex').slice(0, 12).toUpperCase()
  .match(/../g).join(':');

const mirrorArg = arg('mirror', '');
const mirrors = mirrorArg
  ? mirrorArg.split(',').map((s) => s.trim()).filter(Boolean)
  : ['https://ghfast.top/', 'https://gh-proxy.com/', 'https://ghproxy.net/'];

const rawUrl = arg('raw-url',
  `https://raw.githubusercontent.com/${repo}/${branch}/${path}`);

const boot = {
  v: 1,
  repo,
  branch,
  path,
  apiBase,
  token,
  kid,
  masterB64,
  signingPem: pem,
  pubKeyHex,
  rawUrl,
  mirrors,
};

const seq = arg('seq', '');
if (seq) {
  const n = Number(seq);
  if (!Number.isInteger(n) || n < 0) {
    die('--seq 必须是非负整数');
  }
  boot.seq = n;
}

const json = flag('pretty') ? JSON.stringify(boot, null, 2) : JSON.stringify(boot);

const outFile = arg('out', '');
if (outFile) {
  writeFileSync(outFile, json + '\n');
  console.error('');
  console.error(`⚠ 已写入 ${outFile}`);
  console.error('  这个文件含发布私钥。用完请删掉，别提交（.gitignore 已排除 bootstrap*.json）。');
}

console.error('');
console.error('─'.repeat(64));
console.error('bootstrap 已生成：');
console.error(`  仓库      ${repo} @ ${branch}`);
console.error(`  路径      ${path}`);
console.error(`  kid       ${kid}`);
console.error(`  API       ${apiBase}`);
console.error(`  公钥指纹  ${fp}`);
console.error(`  镜像      ${mirrors.length} 个`);
console.error('');
console.error('把下面整段 JSON 复制到手机的 ChaiSync 里（首次启动会自动弹输入框）。');
console.error('它含发布私钥 —— 不要发群、不要存网盘、不要提交 git。');
console.error('─'.repeat(64));
console.error('');

process.stdout.write(json + '\n');
