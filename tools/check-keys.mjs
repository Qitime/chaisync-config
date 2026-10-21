#!/usr/bin/env node
/*
 * 构建后自检：确认「APK 里钉的公钥/master」真的能解开「线上信封」。
 *
 * 为什么需要这个：2026-10-21 的 1.3 就是用错了密钥目录（keys/ 而不是 keys-prod/）打出来的。
 * 三个镜像都能 200 拿到信封，但每一封都被 bad-signature 拒掉，电视端只会在
 * sync_status.txt 里留一行 reject，然后静默保留旧配置进首页——看起来像"加载完就跳过同步"，
 * 排查成本极高。这种错误在编译期完全看不出来，只有拿真实信封试解一次才能发现。
 *
 * 用法（build-gh.sh 会在签名后自动调用）：
 *   node tools/check-keys.mjs --apk dist/Xemby-tv-1.3.apk
 *   node tools/check-keys.mjs --apk dist/Xemby-tv-1.3.apk --env dist/config.enc.json
 *   node tools/check-keys.mjs --apk dist/Xemby-tv-1.3.apk --env https://raw.githubusercontent.com/.../config.enc.json
 *
 * 退出码：0 = 密钥与信封匹配；1 = 不匹配或无法判定（构建应当失败）
 *
 * 零依赖：只用 Node 内置 crypto + 系统自带 unzip 能力（直接读 zip，不调外部命令）。
 */

import { readFileSync } from 'node:fs';
import crypto from 'node:crypto';
import zlib from 'node:zlib';

const NS = 'chai-config-v1';

/* ------------------------------------------------------------------ */
/* 最小 zip 读取：只取 assets/sync_config.json                         */
/* ------------------------------------------------------------------ */

function readZipEntry(zipPath, entryName) {
  const buf = readFileSync(zipPath);

  // 从尾部找 End Of Central Directory（0x06054b50），注释最长 65535
  let eocd = -1;
  const start = Math.max(0, buf.length - 65557);
  for (let i = buf.length - 22; i >= start; i--) {
    if (buf.readUInt32LE(i) === 0x06054b50) {
      eocd = i;
      break;
    }
  }
  if (eocd < 0) throw new Error('不是合法的 zip/apk：找不到 EOCD');

  const count = buf.readUInt16LE(eocd + 10);
  let off = buf.readUInt32LE(eocd + 16);

  for (let i = 0; i < count; i++) {
    if (buf.readUInt32LE(off) !== 0x02014b50) throw new Error('中央目录损坏');
    const method = buf.readUInt16LE(off + 10);
    const compSize = buf.readUInt32LE(off + 20);
    const nameLen = buf.readUInt16LE(off + 28);
    const extraLen = buf.readUInt16LE(off + 30);
    const commentLen = buf.readUInt16LE(off + 32);
    const localOff = buf.readUInt32LE(off + 42);
    const name = buf.toString('utf8', off + 46, off + 46 + nameLen);

    if (name === entryName) {
      // 本地文件头长度可变，必须按它自己的 name/extra 长度跳过
      const lNameLen = buf.readUInt16LE(localOff + 26);
      const lExtraLen = buf.readUInt16LE(localOff + 28);
      const dataStart = localOff + 30 + lNameLen + lExtraLen;
      const raw = buf.subarray(dataStart, dataStart + compSize);
      if (method === 0) return raw;
      if (method === 8) return zlib.inflateRawSync(raw);
      throw new Error('不支持的压缩方式 ' + method);
    }
    off += 46 + nameLen + extraLen + commentLen;
  }
  throw new Error(`APK 里没有 ${entryName}`);
}

/* ------------------------------------------------------------------ */
/* 信封校验（与 tools/publish.mjs 的 openEnv 同构，故意不 import 以保持自检独立） */
/* ------------------------------------------------------------------ */

function signingInput(e) {
  return Buffer.from([
    NS, String(e.v), e.alg, e.kid, String(e.seq), String(e.iat),
    e.n1, e.wdek, e.n2, e.ct,
  ].join('\n'), 'utf8');
}

function hkdf(ikm, salt, info, len = 32) {
  const prk = crypto.createHmac('sha256', salt).update(ikm).digest();
  let out = Buffer.alloc(0);
  let prev = Buffer.alloc(0);
  let counter = 1;
  while (out.length < len) {
    prev = crypto.createHmac('sha256', prk).update(Buffer.concat([prev, info, Buffer.from([counter])])).digest();
    out = Buffer.concat([out, prev]);
    counter++;
  }
  return out.subarray(0, len);
}

function openEnvelope(envJson, { pubKeyHex, masterB64 }) {
  const e = envJson;
  if (e.v !== 1 || e.alg !== 'A256GCM+ES256') {
    return { ok: false, reason: `信封版本/算法不支持（v=${e.v} alg=${e.alg}）` };
  }

  const pub = crypto.createPublicKey({
    key: Buffer.from(pubKeyHex.trim(), 'hex'),
    format: 'der',
    type: 'spki',
  });
  const sigOk = crypto.createVerify('sha256')
    .update(signingInput(e))
    .verify(pub, Buffer.from(e.sig, 'base64url'));
  if (!sigOk) {
    return { ok: false, reason: 'bad-signature（公钥与签发者不匹配）' };
  }

  const master = Buffer.from(masterB64.trim(), 'base64');
  if (master.length !== 32) {
    return { ok: false, reason: `master 长度不对：${master.length} 字节，应为 32` };
  }

  const kek = hkdf(master, Buffer.from(e.kid, 'utf8'), Buffer.from('chai-config-kek-v1', 'utf8'));
  const wdekRaw = Buffer.from(e.wdek, 'base64url');
  const d1 = crypto.createDecipheriv('aes-256-gcm', kek, Buffer.from(e.n1, 'base64url'), { authTagLength: 16 });
  d1.setAAD(Buffer.from(`${NS}|wrap|${e.kid}|${e.seq}`, 'utf8'));
  d1.setAuthTag(wdekRaw.subarray(wdekRaw.length - 16));
  let dek;
  try {
    dek = Buffer.concat([d1.update(wdekRaw.subarray(0, wdekRaw.length - 16)), d1.final()]);
  } catch {
    return { ok: false, reason: 'bad-master（DEK 解不开，master 与信封不匹配）' };
  }

  const ctRaw = Buffer.from(e.ct, 'base64url');
  const d2 = crypto.createDecipheriv('aes-256-gcm', dek, Buffer.from(e.n2, 'base64url'), { authTagLength: 16 });
  d2.setAAD(Buffer.from(`${NS}|payload|${e.kid}|${e.seq}`, 'utf8'));
  d2.setAuthTag(ctRaw.subarray(ctRaw.length - 16));
  let plain;
  try {
    plain = Buffer.concat([d2.update(ctRaw.subarray(0, ctRaw.length - 16)), d2.final()]).toString('utf8');
  } catch {
    return { ok: false, reason: 'bad-payload（正文 GCM 校验失败）' };
  }

  let payload;
  try {
    payload = JSON.parse(plain);
  } catch {
    return { ok: false, reason: '正文不是合法 JSON' };
  }
  return { ok: true, seq: e.seq, kid: e.kid, payload };
}

/* ------------------------------------------------------------------ */

function arg(name, dflt) {
  const i = process.argv.indexOf('--' + name);
  return i > 0 && process.argv[i + 1] ? process.argv[i + 1] : dflt;
}

async function loadEnvelope(src) {
  if (/^https?:\/\//i.test(src)) {
    const r = await fetch(src, { headers: { 'user-agent': 'chai-check-keys/1' } });
    if (!r.ok) throw new Error(`下载信封失败 HTTP ${r.status}`);
    return JSON.parse(await r.text());
  }
  return JSON.parse(readFileSync(src, 'utf8'));
}

async function main() {
  const apk = arg('apk');
  const envSrc = arg('env', '');
  const allowMismatch = process.argv.includes('--allow-mismatch');

  if (!apk) {
    console.error('用法：node tools/check-keys.mjs --apk <apk> [--env <file|url>] [--allow-mismatch]');
    process.exit(2);
  }

  const cfg = JSON.parse(readZipEntry(apk, 'assets/sync_config.json').toString('utf8'));
  const pub = cfg.pubKeyHex || '';
  const master = cfg.masterB64 || '';
  console.log('APK      ' + apk);
  console.log('pubKey   ' + (pub ? pub.slice(0, 24) + '…' + pub.slice(-8) : '（缺失）'));
  console.log('master   ' + (master ? master.slice(0, 8) + '…' : '（缺失）'));
  console.log('rawUrl   ' + (cfg.rawUrl || '（缺失）'));

  // 结构自检：缺任何一样，App 在 run() 里会直接 return，表现成"从不同步"
  const missing = [];
  if (!pub) missing.push('pubKeyHex');
  if (!master) missing.push('masterB64');
  if (!cfg.rawUrl) missing.push('rawUrl');
  if (!Array.isArray(cfg.mirrors) || cfg.mirrors.length === 0) missing.push('mirrors');
  if (missing.length) {
    console.error('\n✗ assets/sync_config.json 不完整，缺：' + missing.join(', '));
    process.exit(1);
  }

  const src = envSrc || cfg.rawUrl;
  let env;
  try {
    env = await loadEnvelope(src);
  } catch (e) {
    // 网络问题不等于密钥错。这里退出码用 2，让调用方能把"验不了"和"验不过"分开。
    console.error(`\n! 取不到信封（${src}）：${e.message}`);
    if (allowMismatch) {
      console.log('  已按 --allow-mismatch 放行（离线构建）');
      process.exit(0);
    }
    console.error('  → 无法验证密钥是否匹配。联网后重跑，或用 --env 指定本地信封。');
    console.error('    （这是网络/路径问题，不是密钥对不上；产物本身可能完全正常）');
    process.exit(2);
  }

  console.log('信封     seq=' + env.seq + ' kid=' + env.kid + '  (' + src + ')');

  const r = openEnvelope(env, { pubKeyHex: pub, masterB64: master });
  if (!r.ok) {
    console.error('\n✗ 密钥与线上信封不匹配：' + r.reason);
    console.error('  这个包装到电视上会表现为「拉不到信封、静默进首页」，不要发出去。');
    console.error('  常见原因：构建时传了 keys/ 的参数，而发布用的是 keys-prod/（或反之）。');
    if (allowMismatch) {
      console.log('  已按 --allow-mismatch 放行');
      process.exit(0);
    }
    process.exit(1);
  }

  const servers = Array.isArray(r.payload.servers) ? r.payload.servers : [];
  console.log('解封     OK  servers=' + servers.length + ' lastUsed=' + (r.payload.lastUsedServerId || '-'));
  for (const s of servers.slice(0, 5)) {
    console.log('         - ' + (s.alias || '?') + '  ' + (s.protocol || '?') + '://' + (s.host || '?') + ':' + (s.port || '?'));
  }
  console.log('\n✓ 密钥与信封匹配，可以发布');
}

main().catch((e) => {
  console.error('自检异常：' + e.message);
  process.exit(1);
});
