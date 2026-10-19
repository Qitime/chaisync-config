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
import { dirname } from 'node:path';
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

function genkey(outDir) {
  mkdirSync(outDir, { recursive: true });
  const { publicKey, privateKey } = crypto.generateKeyPairSync('ec', { namedCurve: 'prime256v1' });
  const pem = privateKey.export({ type: 'pkcs8', format: 'pem' });
  const der = publicKey.export({ type: 'spki', format: 'der' });
  const master = crypto.randomBytes(32);

  writeFileSync(`${outDir}/signing.pem`, pem);
  writeFileSync(`${outDir}/signing.pub.pem`, publicKey.export({ type: 'spki', format: 'pem' }));
  writeFileSync(`${outDir}/pubkey.hex`, der.toString('hex'));
  writeFileSync(`${outDir}/master.b64`, master.toString('base64'));

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
  genkey(arg('out', 'keys'));
} else if (cmd === 'publish') {
  const inFile = arg('in');
  const outFile = arg('out');
  const keyFile = arg('key', 'keys/signing.pem');
  if (!inFile || !outFile) die('缺少 --in / --out');
  doPublish({
    inFile,
    outFile,
    keyPem: readFileSync(keyFile),
    master: needMaster(),
    kid: arg('kid', 'k1'),
    seq: arg('seq'),
  });
} else if (cmd === 'refresh') {
  const inFile = arg('in');
  const outFile = arg('out');
  const keyFile = arg('key', 'keys/signing.pem');
  if (!inFile || !outFile) die('缺少 --in / --out');
  await doRefresh({
    inFile,
    outFile,
    keyPem: readFileSync(keyFile),
    master: needMaster(),
    kid: arg('kid', 'k1'),
    seq: arg('seq'),
  });
} else if (cmd === 'verify') {
  const inFile = arg('in');
  const pubFile = arg('pub', 'keys/pubkey.hex');
  if (!inFile) die('缺少 --in');
  doVerify({ inFile, pubFile, master: needMaster() });
} else {
  console.log('用法见文件头注释：genkey | publish | refresh | verify');
  process.exit(1);
}
