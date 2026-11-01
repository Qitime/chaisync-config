#!/usr/bin/env node
/**
 * 密钥组指纹台账（单一事实源）。
 *
 * 为什么需要：2026-10-21 / 2026-11-01 两次事故都是「信封与 APK 用了不同的密钥组」
 * 造成的（keys/ vs keys-prod/）。这类错误编译期看不到，只有解锁真信封才能发现。
 * 本工具给每个密钥目录生成 keyset.json：内含该组的指纹，供发布、构建、CI 三方比对。
 *
 * 用法：
 *   node tools/ensure-keyset.mjs --dir keys-prod
 *      → 生成/更新 keys-prod/keyset.json（幂等，缺什么补什么）
 *
 *   node tools/ensure-keyset.mjs --dir keys-prod --pin <hex> [--strict]
 *      → 校验该目录公钥指纹是否等于 pin（--strict 时不等则以退出码 1 失败），
 *        用于 CI：发布前先证明「私钥确实是 expected 的那把」。
 *
 * 指纹算法：pubkey.hex 的 SHA-256 前 16 字节 hex（同 EnvelopeSeal 客户端核对）。
 */

import { readFileSync, writeFileSync, existsSync, mkdirSync } from 'node:fs';
import { join } from 'node:path';
import crypto from 'node:crypto';

function arg(name, dflt) {
  const i = process.argv.indexOf('--' + name);
  return i > 0 && process.argv[i + 1] ? process.argv[i + 1] : dflt;
}
function die(msg) {
  console.error('✗ ' + msg);
  process.exit(1);
}

const dir = arg('dir', 'keys');
const pin = arg('pin', '');
const strict = process.argv.includes('--strict');

if (!existsSync(join(dir, 'pubkey.hex'))) {
  die(`找不到 ${dir}/pubkey.hex，请先 node tools/publish.mjs genkey --out ${dir}`);
}

const pubkeyHex = readFileSync(join(dir, 'pubkey.hex'), 'utf8').trim();
// 指纹 = SHA-256(SPKI DER 字节) 前 16 字节 hex，与 publish.mjs / check-keys.mjs / 客户端一致
const fingerprint = crypto.createHash('sha256').update(Buffer.from(pubkeyHex, 'hex')).digest('hex').slice(0, 32);
const masterB64 = existsSync(join(dir, 'master.b64'))
  ? readFileSync(join(dir, 'master.b64'), 'utf8').trim()
  : '';
const hasPem = existsSync(join(dir, 'signing.pem'));

// 私钥导出的公钥（防止 pem 与 pubkey.hex 不是一对——老事故的另一个变体）
let pemFp = null;
if (hasPem) {
  try {
    const pem = readFileSync(join(dir, 'signing.pem'), 'utf8');
    const pub = crypto.createPublicKey(pem).export({ type: 'spki', format: 'der' });
    pemFp = crypto.createHash('sha256').update(pub).digest('hex').slice(0, 32);
  } catch (e) {
    die(`signing.pem 无法解析：${e.message}`);
  }
}
if (pemFp && pemFp !== fingerprint) {
  die(`${dir} 自相矛盾：signing.pem 导出的公钥指纹 ${pemFp} ≠ pubkey.hex 指纹 ${fingerprint}。` +
      '说明这个目录被混拼过，绝不能用于发布。');
}

const meta = {
  name: dir,
  fingerprint,
  pubkey_hex_sha256_prefix: fingerprint,
  master_b64_present: !!masterB64,
  signing_pem_present: hasPem,
  created_at: new Date().toISOString(),
};
writeFileSync(join(dir, 'keyset.json'), JSON.stringify(meta, null, 2) + '\n');

console.log(`✓ ${dir}/keyset.json`);
console.log(`  fingerprint = ${fingerprint}`);
console.log(`  master.b64 = ${masterB64 ? '在' : '缺失'}   signing.pem = ${hasPem ? '在' : '缺失'}`);

if (pin) {
  const ok = fingerprint === String(pin).toLowerCase();
  console.log(`  期望 pin  = ${String(pin).toLowerCase()}`);
  if (!ok && strict) {
    die(`指纹不匹配（${fingerprint} ≠ ${pin}）：私钥不是期望那一把，禁止继续。`);
  }
  if (!ok) {
    console.log('  ! 指纹不匹配（非严格模式，仅提示）');
  }
}
