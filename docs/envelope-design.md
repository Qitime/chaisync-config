# 信封（Envelope）设计与发布安全规范

> 适用组件：`tools/publish.mjs`（发布）、`tools/check-keys.mjs`（自检）、`tools/ensure-keyset.mjs`（指纹台账）、`tools/publish-safe.sh`（一键发布）、`.github/workflows/publish-manual.yml`（CI）、客户端 `Envelope/Store/Probe/SelfHeal`。
> 本文件回答：信封为什么是这样、什么操作会导致事故、以及如何让事故在发生前被拦截。

---

## 0. 结论（TL;DR）

1. **密钥只有一把能发布**：线上信封必须由 `keys-prod/` 签发，指纹 `a2bd28aca42dd2cd93009d50cad98f38`。任何工具/CI 在私钥指纹不等于台账值时直接拒绝（`--allow-key-mismatch` 仅限演练）。
2. **seq 只升不降**：新信封 seq 必须 > 线上现有 seq，否则客户端判 `rollback` 拒绝更新。本地没有 out 文件时会尝试从线上取 seq+1，取不到就阻止发布。
3. **CI 不再定时自动发布**：默认仅手动触发；无人值守定时发布已移除，避免把错密钥/错内容静默推出去。
4. **客户端信任链不变**：APK 内钉 pubkey+master，远端信封验签+解密+seq 单调+镜像容错。服务端侧的防呆是本次重点，客户端侧维持现状。

---

## 1. 信封是什么

信封是“公网仓库里的密文”。三层安全：

| 层 | 机制 | 防什么 |
|---|---|---|
| 保密 | AES-256-GCM（master→HKDF→kek→wdek→dek→ct） | 镜像站/爬虫/公开仓库读者拿不到明文 |
| 真实 | ECDSA-P256，`signingInput()` 固定字段序 | 镜像站无法伪造/篡改内容 |
| 新鲜 | seq 单调递增 + iat 时间戳 | 回滚旧信封（即使签名合法） |

`signingInput()` 对字段 `{v, alg, kid, seq, iat, n1, wdek, n2, ct}` 用 `\n` 拼接后签名，**绝不直接签 JSON 文本**（键序/空格两端不一致会误判）。

---

## 2. 事故史（为什么这么设计）

| 时间 | 事故 | 根因 | 现在的拦截 |
|---|---|---|---|
| 2026-10-21 | 电视 1.3 拉不到信封，静默进首页 | 打包 APK 时注入了 `keys/`（演练组），线上信封却是 `keys-prod/` 签发 | `check-keys --expect prod` 指纹断言；`build-gh.sh` 内置自检 |
| 2026-11-01 | 电视“添加服务器”（bad-signature） | 线上信封被 `keys/` 重签（seq=2→3），APK 钉的是 keys-prod 公钥 | `publish --keys-dir keys-prod` 指纹断言；CI PIN 审计 |
| 2026-11-01 | 自愈版真机崩溃 | 重登网络在主线程（NetworkOnMainThreadException）；缺授权头 400；自签名证书 SSL 失败 | 自愈已改线程+join、补头、逐台遍历 |

**通用教训**：密钥放错套编译期看不出来，必须把「发布私钥指纹 == APK 内公钥指纹 == 台账指纹」变成机器强制，而不是人等。

---

## 3. 密钥组与指纹台账

- 每个密钥目录：`signing.pem`（私钥）+ `pubkey.hex`（公钥 DER）+ `master.b64`（主密钥）+ `keyset.json`（指纹元数据）。
- **指纹定义**（全链路一致）：`SHA-256(pubkey DER 字节)` 前 16 字节 hex。
- `config/expected-keyset.json` 是仓库内的“唯一权威台账”，只放指纹和 rawUrl，**不含任何私钥/主密钥**，可安全入库。
- `tools/ensure-keyset.mjs`：生成/校验 keyset.json，并检查目录内 pem 与 pubkey 是否自洽（发现混拼立即失败）。

```
node tools/ensure-keyset.mjs --dir keys-prod --pin a2bd28aca42dd2cd93009d50cad98f38 --strict
```

## 4. 发布命令规范

```bash
# 生成新密钥组（演练/dev 常用）
node tools/publish.mjs genkey --out keys --role dev

# 正式发布（推荐，目录直读+指纹断言+验签+信封自检）
tools/publish-safe.sh                     # 生成 dist/config.enc.json 并自检
tools/publish-safe.sh --push              # 生成并自动 commit+push

# 手动逐条（等效）
node tools/publish.mjs publish --in config/config.json --out dist/config.enc.json \
     --keys-dir keys-prod                 # 指纹≠台账直接拒绝
node tools/publish.mjs verify --in dist/config.enc.json --keys-dir keys-prod
node tools/check-keys.mjs --envelope dist/config.enc.json --keys-dir keys-prod --expect prod

# 演练/强制（仅本地测试，永不用于线上）
node tools/publish.mjs publish --in config/config.json --out /tmp/x.enc.json \
     --keys-dir keys --allow-key-mismatch
```

要点：
- 推荐 `--keys-dir`，自动读 pem/master/pubkey 三件套并做“自洽+台账”断言；不再手抄 `--key/--master`。
- seq 自动：`--out` 已有则 +1；否则尝试从线上 rawUrl 取 seq+1；取不到则阻止，必须显式 `--seq`。
- 发布后 verify + check-keys 双保险。

## 5. CI（`.github/workflows/publish-manual.yml`）

- 触发：`workflow_dispatch` 手动。**已移除 schedule**（防止无人值守误发）。
- 闸门 1：`PUBLIC_KEY_PIN` secret 与 `SIGNING_KEY` 私钥指纹比对，不符即失败，不发任何内容。
- 闸门 2：publish 内部再按仓库内 `config/expected-keyset.json` 断言一次。
- 产物仅提交 `dist/config.enc.json`；临时明文/私钥在步骤结尾删除。
- 需要的 Secrets：
  - `CONFIG_JSON`：完整明文（含账密）JSON
  - `MASTER_B64`：`keys-prod/master.b64`
  - `SIGNING_KEY`：`keys-prod/signing.pem`
  - `PUBLIC_KEY_PIN`：`a2bd28aca42dd2cd93009d50cad98f38`
- 恢复定时发布：显式加回 `on.schedule`，并确认 PIN 审计通过、明文模型仍适用。

## 6. 客户端侧信任链与行为

- 启动 → 从镜像竞速拉信封 → 验签（pubkey 钉在 APK assets）→ 解密 → seq 单调校验 → 写 prefs。
- 故障容忍：`rollback` 视为“本地更新”，继续用旧配置；`bad-signature` 只留日志，不覆盖。
- 自愈：同一信封无 token 时，用明文账密对每台服务器逐台 `AuthenticateByName`，成功后写回 token 并切换 `last_used_server`；网络故障逐台继续，不阻塞。

## 7. 轮换（换密钥）计划

1. 新密钥组 `keys-prod-v2` genkey，把 pubkey/master 注入**下一版 APK**并发布。
2. `config/expected-keyset.json` 增加 `prod-v2` 条目；发布端支持 `--expect prod-v2`。
3. 在电视端升级覆盖旧 APK 后再切发布密钥；旧信封保持可验签（多 kid 并存）。
4. 全部设备升级后删除旧条目。
> 客户端当前仅单 kid/单公钥；多 kid 并存属于以后的功能扩展，本文记录意图，不做代码承诺。

## 8. 故障排查矩阵

| 现象 | 可能原因 | 查什么 |
|---|---|---|
| 电视日志 `bad-signature` | 信封与 APK 公钥不同套 | `check-keys --apk <apk> --env <信封> --expect prod` |
| 电视 `reject rollback seq<=last` | 信封 seq ≤ 电视已用 seq | 对比 `dist/config.enc.json` 与 sync_status 最后应用 seq |
| 电视只留日志不进首页 | assets 缺 pubKeyHex/masterB64/rawUrl/mirrors | `check-keys` 结构自检 |
| 自愈日志 `SSLHandshakeException` | 服务器证书过期/自签 | `curl -k` 测试 + `openssl s_client` 看证书有效期 |
| 自愈 `login-failed http 400` | 缺 `X-Emby-Authorization` 头 | 服务端响应体；客户端需补头 |
| CI 失败 `指纹 ≠ PIN` | Secrets 里 SIGNING_KEY 不是 keys-prod | 核对 `PUBLIC_KEY_PIN` 与本地 `keys-prod/keyset.json` |
