#!/usr/bin/env node
/**
 * ChaiSync-GH 配置发布工具。零依赖，只用 Node 内置 crypto。
 *
 * 三层各管一件事，不要用其中一层去顶另外两层：
 *   1) AES-256-GCM  → 保密性：镜像站 / 爬虫 / 公开库读者只看到密文
 *   2) ECDSA-P256   → 真实性：只有你的私钥能签发，镜像站换不掉内容
 *   3) seq 单调计数 → 防回滚：旧信封签名也合法，但不许再被采用
 *
 * 用法：
 *   node tools/publish.mjs genkey  --out keys
 *   node tools/publish.mjs publish --in config/config.json --out dist/config.enc.json \
 *        --key keys/signing.pem --master <MASTER_B64> --kid k1
 *   node tools/publish.mjs refresh --in config/config.json --out dist/config.enc.json \
 *        --key keys/signing.pem --master <MASTER_B64>
 *   node tools/publish.mjs verify  --in dist/config.enc.json --pub keys/pubkey.hex --master <MASTER_B64>
 *
 * seq 默认自动递增（读 --out 里已有信封的 seq 再 +1），不用手填。
 * 手填是这套流程里最容易出的事故：填小了会让所有电视从此拒绝更新，而且现象是"静默不更新"。
 */
import { readFileSync, writeFileSync, mkdirSync, existsSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import crypto from 'node:crypto';

const NS = 'chai-config-v1';
const b64u = (b) => Buffer.from(b).toString('base64url');

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

// 仓库根：本文件位于 <root>/tools/。指纹台账 config/expected-keyset.json 存于此。
const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');

function sha256hex(b) {
  return crypto.createHash('sha256').update(b).digest('hex');
}

/** 读取 config/expected-keyset.json（发布端“唯一权威指纹”）；不存在返回 null */
function loadExpectedKeyset() {
  const p = join(ROOT, 'config', 'expected-keyset.json');
  if (!existsSync(p)) return null;
  try {
    return JSON.parse(readFileSync(p, 'utf8'));
  } catch {
    return null;
  }
}

/** 从私钥 PEM 导出公钥指纹（16 字节 hex） */
function pemFingerprint(keyPem) {
  const pub = crypto.createPublicKey(keyPem).export({ type: 'spki', format: 'der' });
  return sha256hex(pub).slice(0, 32);
}

/** 枚举密钥目录，读取 pem/master/pubkey 三件套 + 指纹 */
function loadKeysDir(dir) {
  const pem = join(dir, 'signing.pem');
  const master = join(dir, 'master.b64');
  const pub = join(dir, 'pubkey.hex');
  if (!existsSync(pem) || !existsSync(master) || !existsSync(pub)) {
    die(`密钥目录 ${dir} 不完整：需要 signing.pem / master.b64 / pubkey.hex 三个文件`);
  }
  const keyPem = readFileSync(pem);
  const masterB64 = readFileSync(master, 'utf8').trim();
  const pubKeyHex = readFileSync(pub, 'utf8').trim();
  const fp = pemFingerprint(keyPem);
  const pubFp = sha256hex(Buffer.from(pubKeyHex, 'hex')).slice(0, 32);
  if (fp !== pubFp) {
    die(`${dir} 自相矛盾：signing.pem 指纹 ${fp} ≠ pubkey.hex 指纹 ${pubFp}。请用 ensure-keyset.mjs 排查。`);
  }
  return { keyPem, masterB64, pubKeyHex, fingerprint: fp };
}

/** 指纹断言：发布私钥必须等于期望台账（--expect <name> 或默认 prod） */
function assertExpectedFp(fp, expectName) {
  const exp = loadExpectedKeyset();
  if (!exp) {
    console.log('  （没有 config/expected-keyset.json，跳过发布指纹断言）');
    return;
  }
  const key = expectName || 'prod';
  const pin = exp[key] && exp[key].fingerprint;
  if (!pin) {
    console.log(`  （指纹台账里没有 ${key}，跳过断言）`);
    return;
  }
  if (fp !== pin) {
    die(`发布私钥指纹 ${fp} ≠ expected[${key}] ${pin}。` +
        '这会把一封电视端永远拒收的信封推上去（bad-signature）。' +
        '要么换正确密钥（--keys-dir keys-prod），要么显式 --allow-key-mismatch（演练用）。');
  }
  console.log(`  ✓ 指纹断言通过：expected[${key}] = ${pin}`);
}

/** 线上 seq 读取：本地 outFile 不存在时防止误发 seq=1 导致全电视回滚 */
async function liveSeq(rawUrl) {
  if (!rawUrl) return null;
  try {
    const r = await fetch(rawUrl, { headers: { 'user-agent': 'chai-publish/1' }, signal: AbortSignal.timeout(10000) });
    if (!r.ok) return null;
    const env = JSON.parse(await r.text());
    return Number.isInteger(env.seq) && env.seq > 0 ? env.seq : null;
  } catch {
    return null;
  }
}

/* ------------------------------------------------------------------ */
/* 密码学                                                              */
/* ------------------------------------------------------------------ */

/** HKDF-SHA256 (RFC 5869)；Android 端 Envelope.hkdf() 是同一套 */
function hkdf(ikm, salt, info, len = 32) {
  const prk = crypto.createHmac('sha256', salt).update(ikm).digest();
  const blocks = [];
  let prev = Buffer.alloc(0);
  for (let counter = 1; Buffer.concat(blocks).length < len; counter++) {
    prev = crypto.createHmac('sha256', prk)
      .update(Buffer.concat([prev, Buffer.from(info), Buffer.from([counter])]))
      .digest();
    blocks.push(prev);
  }
  return Buffer.concat(blocks).subarray(0, len);
}

/** 签名输入：逐字段换行拼接。绝不能直接对 JSON 文本签名（键序/空白两端不一致会误判） */
function signingInput(e) {
  return Buffer.from(
    [NS, e.v, e.alg, e.kid, e.seq, e.iat, e.n1, e.wdek, e.n2, e.ct].join('\n'),
    'utf8'
  );
}

function seal({ plaintext, master, kid, seq, keyPem }) {
  const kek = hkdf(master, Buffer.from(kid, 'utf8'), Buffer.from('chai-config-kek-v1', 'utf8'));
  const dek = crypto.randomBytes(32);
  // nonce 每次发布必须随机。复用 nonce = 密钥流重复 = 明文可被还原，绝不能拿 seq 当 nonce。
  const n1 = crypto.randomBytes(12);
  const n2 = crypto.randomBytes(12);

  const c1 = crypto.createCipheriv('aes-256-gcm', kek, n1, { authTagLength: 16 });
  c1.setAAD(Buffer.from(`${NS}|wrap|${kid}|${seq}`, 'utf8'));
  const wdek = Buffer.concat([c1.update(dek), c1.final(), c1.getAuthTag()]); // 32 + 16

  const c2 = crypto.createCipheriv('aes-256-gcm', dek, n2, { authTagLength: 16 });
  c2.setAAD(Buffer.from(`${NS}|payload|${kid}|${seq}`, 'utf8'));
  const ct = Buffer.concat([c2.update(plaintext), c2.final(), c2.getAuthTag()]);

  const env = {
    v: 1,
    alg: 'A256GCM+ES256',
    kid,
    seq: Number(seq),
    iat: Math.floor(Date.now() / 1000),
    n1: b64u(n1),
    wdek: b64u(wdek),
    n2: b64u(n2),
    ct: b64u(ct),
  };
  env.sig = b64u(crypto.createSign('sha256').update(signingInput(env)).sign(keyPem));
  return env;
}

function openEnv({ json, master, pubKeyHex }) {
  const e = json;
  if (e.alg !== 'A256GCM+ES256' || e.v !== 1) throw new Error('信封版本或算法不支持');

  const input = signingInput(e);
  const keyDer = Buffer.from(pubKeyHex.trim(), 'hex');
  const pubKey = crypto.createPublicKey({ key: keyDer, format: 'der', type: 'spki' });
  if (!crypto.createVerify('sha256').update(input).verify(pubKey, Buffer.from(e.sig, 'base64url'))) {
    throw new Error('签名不匹配');
  }

  const kek = hkdf(master, Buffer.from(e.kid, 'utf8'), Buffer.from('chai-config-kek-v1', 'utf8'));
  const wdekRaw = Buffer.from(e.wdek, 'base64url');
  const d1 = crypto.createDecipheriv('aes-256-gcm', kek, Buffer.from(e.n1, 'base64url'), { authTagLength: 16 });
  d1.setAAD(Buffer.from(`${NS}|wrap|${e.kid}|${e.seq}`, 'utf8'));
  d1.setAuthTag(wdekRaw.subarray(wdekRaw.length - 16));
  const dek = Buffer.concat([d1.update(wdekRaw.subarray(0, wdekRaw.length - 16)), d1.final()]);

  const ctRaw = Buffer.from(e.ct, 'base64url');
  const d2 = crypto.createDecipheriv('aes-256-gcm', dek, Buffer.from(e.n2, 'base64url'), { authTagLength: 16 });
  d2.setAAD(Buffer.from(`${NS}|payload|${e.kid}|${e.seq}`, 'utf8'));
  d2.setAuthTag(ctRaw.subarray(ctRaw.length - 16));
  return Buffer.concat([d2.update(ctRaw.subarray(0, ctRaw.length - 16)), d2.final()]).toString('utf8');
}

/* ------------------------------------------------------------------ */
/* 命令                                                                */
/* ------------------------------------------------------------------ */

function genkey(outDir, role) {
  mkdirSync(outDir, { recursive: true });
  const { publicKey, privateKey } = crypto.generateKeyPairSync('ec', { namedCurve: 'prime256v1' });
  const pem = privateKey.export({ type: 'pkcs8', format: 'pem' });
  const der = publicKey.export({ type: 'spki', format: 'der' });
  const master = crypto.randomBytes(32);

  writeFileSync(`${outDir}/signing.pem`, pem);
  writeFileSync(`${outDir}/signing.pub.pem`, publicKey.export({ type: 'spki', format: 'pem' }));
  writeFileSync(`${outDir}/pubkey.hex`, der.toString('hex'));
  writeFileSync(`${outDir}/master.b64`, master.toString('base64'));

  // 指纹台账：让发布/构建/CI 三方有同一把尺子
  const fp = sha256hex(Buffer.from(der.toString('hex'), 'hex')).slice(0, 32);
  writeFileSync(`${outDir}/keyset.json`, JSON.stringify({
    name: outDir,
    role: role || 'dev',
    fingerprint: fp,
    pubkey_hex_sha256_prefix: fp,
    created_at: new Date().toISOString(),
  }, null, 2) + '\n');

  console.log('已生成：');
  console.log('  ' + outDir + '/signing.pem    私钥，绝不入库（.gitignore 已排除）');
  console.log('  ' + outDir + '/pubkey.hex     公钥，钉进 APK');
  console.log('  ' + outDir + '/master.b64     主密钥，同时进构建机和 APK');
  console.log('');
  console.log('PUBKEY_HEX=' + der.toString('hex'));
  console.log('MASTER_B64=' + master.toString('base64'));
  console.log('');
  console.log('下一步：powershell -File build/build.ps1 -MasterB64 <上面的 MASTER_B64> \\');
  console.log('          -PubKeyHex <上面的 PUBKEY_HEX> -RawBase https://raw.githubusercontent.com/<你>/<仓库>/main/dist/config.enc.json');
}

function nextSeq(outFile, explicit) {
  let prev = null;
  if (existsSync(outFile)) {
    try {
      prev = JSON.parse(readFileSync(outFile, 'utf8')).seq ?? null;
    } catch {
      prev = null;
    }
  }
  if (explicit !== undefined && explicit !== null) {
    const n = Number(explicit);
    if (!Number.isInteger(n) || n <= 0) die('--seq 必须是正整数');
    if (prev !== null && n <= prev) {
      die(`--seq ${n} 不大于已有信封的 ${prev}。这会让所有客户端把它判为 rollback 并永久拒绝更新。`);
    }
    return n;
  }
  return prev === null ? 1 : prev + 1;
}

function doPublish({ inFile, outFile, keyPem, master, kid, seq }) {
  let raw = readFileSync(inFile, 'utf8');
  let cfg;
  try {
    cfg = JSON.parse(raw);
  } catch (e) {
    die('明文配置不是合法 JSON: ' + e.message);
  }

  const n = nextSeq(outFile, seq);
  cfg.seq = n;

  // 密码在密文里，留在里面不会泄露；但如果你想让泄露时的爆炸半径只有可吊销的 token，
  // 加 --strip-password：代价是 App 无法自动重新登录，只能在 token 过期后重新下发。
  if (flag('strip-password')) {
    for (const s of cfg.servers || []) delete s.password;
    console.log('  （已按 --strip-password 剔除 password 字段）');
  }

  const plaintext = Buffer.from(JSON.stringify(cfg), 'utf8');
  const env = seal({ plaintext, master, kid, seq: n, keyPem });

  mkdirSync(dirname(outFile), { recursive: true });
  writeFileSync(outFile, JSON.stringify(env, null, 2) + '\n');

  const size = Buffer.byteLength(JSON.stringify(env)) / 1024;
  console.log(`✓ ${outFile}`);
  console.log(`  seq=${n}  kid=${kid}  明文 ${plaintext.length}B → 信封 ${size.toFixed(1)}KiB`);
  console.log(`  servers=${(cfg.servers || []).length}  lastUsed=${cfg.lastUsedServerId || '(首个)'}`);
  console.log('');
  console.log('  提醒：raw 有 max-age=300，发布后最多 5 分钟才全网可见。客户端会带 ?v= 缓存键，');
  console.log('        检测到 seq 不前进时会自动换键重问一次，所以不用反复重发。');
}

async function doRefresh({ inFile, outFile, keyPem, master, kid, seq }) {
  const cfg = JSON.parse(readFileSync(inFile, 'utf8'));
  let ok = 0;
  let fail = 0;

  for (const s of cfg.servers || []) {
    if (!s.username || !s.password) {
      console.log(`- ${s.alias || s.id}: 没配 username/password，跳过`);
      continue;
    }
    const scheme = s.protocol || 'http';
    const path = s.path ? '/' + String(s.path).replace(/^\/+/, '') : '';
    const base = `${scheme}://${s.host}:${s.port || 8096}${path}`;
    const deviceId = s.id || 'chaisync';
    try {
      const res = await fetch(`${base}/Users/AuthenticateByName`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // Emby 要求的授权头形态；少了它有的服务端直接 400
          'X-Emby-Authorization':
            `MediaBrowser Client="ChaiSync", Device="publish", DeviceId="${deviceId}", Version="1.0"`,
        },
        body: JSON.stringify({ Username: s.username, Pw: s.password }),
        signal: AbortSignal.timeout(15000),
      });
      if (!res.ok) {
        fail++;
        console.log(`✗ ${s.alias || s.id}: HTTP ${res.status} ${await res.text().catch(() => '')}`.trim());
        continue;
      }
      const j = await res.json();
      const token = j.AccessToken || j.accessToken;
      const uid = (j.User && j.User.Id) || j.UserId;
      if (!token || !uid) {
        fail++;
        console.log(`✗ ${s.alias || s.id}: 响应里没有 AccessToken/UserId`);
        continue;
      }
      s.accessToken = token;
      s.userId = uid;
      s.isVerified = true;
      s.lastLoginTime = Date.now();
      ok++;
      console.log(`✓ ${s.alias || s.id}: token 已刷新 (${String(token).slice(0, 6)}…)`);
    } catch (e) {
      fail++;
      console.log(`✗ ${s.alias || s.id}: ${e.message}`);
    }
  }

  if (ok === 0) {
    die('一个都没刷新成功，不发布（避免把过期 token 推上去）');
  }
  // 把刷新后的明文写回本地，下次 refresh 不必重新登录全部账号
  writeFileSync(inFile, JSON.stringify(cfg, null, 2) + '\n');
  console.log(`\n${ok} 成功 / ${fail} 失败，开始发布`);
  doPublish({ inFile, outFile, keyPem, master, kid, seq });
}

function doVerify({ inFile, pubFile, master }) {
  const json = JSON.parse(readFileSync(inFile, 'utf8'));
  const pubKeyHex = readFileSync(pubFile, 'utf8');
  try {
    const pt = openEnv({ json, master, pubKeyHex });
    const obj = JSON.parse(pt);
    console.log('✓ 验签通过，seq=' + json.seq + ' kid=' + json.kid);
    console.log('  明文 ' + Buffer.byteLength(pt, 'utf8') + 'B，servers=' + (obj.servers || []).length);
    console.log('  ' + JSON.stringify(obj, null, 2).split('\n').join('\n  '));
  } catch (e) {
    console.error('✗ ' + e.message);
    process.exit(3);
  }
}

/* ------------------------------------------------------------------ */

const cmd = process.argv[2];
const masterB64 = arg('master');
const needMaster = () => {
  if (!masterB64) die('缺少 --master');
  const m = Buffer.from(masterB64, 'base64');
  if (m.length !== 32) die('master 必须是 32 字节（base64 后 44 字符）');
  return m;
};

if (cmd === 'genkey') {
  genkey(arg('out', 'keys'), arg('role', 'dev'));
} else if (cmd === 'publish' || cmd === 'refresh') {
  const inFile = arg('in');
  const outFile = arg('out');
  if (!inFile || !outFile) die('缺少 --in / --out');

  // 密钥来源：优先 --keys-dir（目录直读，防手抄）；老 --key/--master 仍兼容
  let keyPem, masterB64, pubFp;
  const keysDir = arg('keys-dir', '');
  if (keysDir) {
    const k = loadKeysDir(keysDir);
    keyPem = k.keyPem;
    masterB64 = k.masterB64;
    pubFp = k.fingerprint;
  } else {
    keyPem = readFileSync(arg('key', 'keys/signing.pem'));
    masterB64 = needMaster().toString('base64');
    pubFp = pemFingerprint(keyPem);
  }

  // 指纹断言：不匹配直接拒绝发布（除非显式 --allow-key-mismatch）
  if (!process.argv.includes('--allow-key-mismatch')) {
    assertExpectedFp(pubFp, arg('expect', 'prod'));
  }

  // 防回滚：本地没有 outFile 时尝试从线上取 seq+1（防止误发 seq=1 导致全电视拒绝更新）
  let seq = arg('seq');
  if (seq === undefined) {
    if (!existsSync(outFile)) {
      const rawUrl = arg('live-url', '') || (loadExpectedKeyset()?.prod?.rawUrl) || '';
      const lv = rawUrl ? await liveSeq(rawUrl) : null;
      if (lv !== null) {
        seq = String(lv + 1);
        console.log(`  （out 不存在，取线上 seq=${lv} → 新 seq=${seq}）`);
      } else if (!process.argv.includes('--seq-live-skip')) {
        die('out 文件不存在且无法读取线上 seq，请显式 --seq <N>（必须大于线上最新 seq）');
      }
    }
  }
  const master = Buffer.from(masterB64, 'base64');
  if (master.length !== 32) die('master 必须是 32 字节（base64 后 44 字符）');

  if (cmd === 'publish') {
    doPublish({ inFile, outFile, keyPem, master, kid: arg('kid', 'k1'), seq });
  } else {
    await doRefresh({ inFile, outFile, keyPem, master, kid: arg('kid', 'k1'), seq });
  }
} else if (cmd === 'verify') {
  const inFile = arg('in');
  if (!inFile) die('缺少 --in');
  const keysDir = arg('keys-dir', '');
  let pubFile = arg('pub', 'keys/pubkey.hex');
  let master;
  if (keysDir) {
    const k = loadKeysDir(keysDir);
    pubFile = join(keysDir, 'pubkey.hex');
    master = Buffer.from(k.masterB64, 'base64');
  } else {
    master = needMaster();
  }
  doVerify({ inFile, pubFile, master });
} else {
  console.log('用法见文件头注释：genkey | publish | refresh | verify');
  console.log('安全用法：node tools/publish.mjs publish --in config/config.json --out dist/config.enc.json --keys-dir keys-prod');
  process.exit(1);
}
