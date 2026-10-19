# ChaiSync Admin（子 APK）

装在自己手机上的**发布端**：本地管服务器账号 → 加密签名 → 推到 GitHub。
电视上那个（母 APK，见 [`../README.md`](../README.md)）只负责读回来、验签、解密、写进 prefs。

## 一句话分工

| | 母 APK（电视） | 子 APK（手机） |
|---|---|---|
| 装在哪 | 电视 | 手机 |
| 干什么 | 消费配置 | 生产配置 |
| 手上的密钥 | 公钥 + master | **私钥** + master + GitHub PAT |
| 联网时机 | 启动探测失败 / 首次运行 | 手动发布、手动刷令牌 |
| 有没有界面 | 没有（ContentProvider 借用启动时机） | 有 |

子 APK 不做任何后台联网。它不在后台轮询 GitHub，也没有定时任务——只在按下按钮的那一刻连网。

## 为什么发布私钥不在 APK 里

母 APK 里放 `master` 是安全的：拿到它只能**解开**你已经发布的东西，不能**伪造**。

子 APK 不一样。它要签发信封，就必须有私钥。而 APK 一旦装到任何一台设备上，包里的常量就能被 `jadx` 三分钟翻出来。**私钥编进 APK = 任何拿到安装包的人都能发出你的电视会接受的信封。** 这是整套方案里唯一一处"泄露即失守"的东西，所以处理方式和 master 完全不同：

- 私钥**不参与构建**，`build-apk.sh` 从不读取它；
- 首次使用时由你粘贴一段 bootstrap JSON 导入；
- 导入后存在 `vault.bin`，用 AndroidKeyStore 里**不可导出**的 AES-256 密钥加密；
- bootstrap JSON 走完这一次就可以删掉。

代价写在明处：**换手机不能靠备份迁移**，只能重新粘一次。因为能备份的私钥就是能复制的私钥。

## 构建

```bash
bash build-apk.sh
# → dist/ChaiSync-admin-1.0.7.apk  （约 53 KiB）
```

不依赖 Gradle、不联网、不下载任何东西，只用 build-tools 自带的 `aapt2 / javac / d8 / zipalign / apksigner`。所以整个 App 是 framework API + 代码构建界面，没有 AndroidX。

签名密钥在 `build/chaisync-admin.jks`（口令 `chaisync-admin`）。**丢了这个文件就无法覆盖安装升级**，只能卸载重装。

## 首次使用

**① 电脑上生成 bootstrap**

```bash
node tools/bootstrap.mjs --repo owner/config-repo --token github_pat_xxx
```

> 仓库**必须是公开的**。母 APK 拉取时不带 `Authorization`（只发 UA 和 Accept），镜像站也只代理公开内容——私有库在电视端会稳定 404。机密性靠信封加密，不靠仓库私有。

token 也可以走环境变量 `GH_TOKEN`，避免留在 shell 历史里。加 `--out dist/bootstrap.json` 直接落盘，然后做成可一键复制的页面：

```bash
node tools/make-bootstrap-page.mjs      # 生成 dist/bootstrap.html
```

之后双击 `dist/打开导入页.bat`：它把 JSON 放进剪贴板并打开页面，粘到微信文件传输助手发给自己即可。手机上别试图打开 `.json` 文件——多数手机没有合适的查看器，也没法整段选中。

**② 手机上导入**

装 APK → 首启自动弹输入框 → 粘贴 → 导入。

导入时会**当场做一次封装往返**：用 `signingPem` 签、用 `pubKeyHex` 验、用 `master` 解。这一步是为了拦住"重新 genkey 后只换了部分文件"这类事故——那种错误如果留到第一次发布，结果是推出去一封电视打不开的信封，而电视只会静默不更新。

**③ 日常三步**

```
排序  →  勾选服务器  →  刷新选中  →  发布选中
```

这三步都在主界面，但碰的是**两套完全不同的配置**，别混：

| 界面位置 | 配的是什么 | 填几次 |
|---|---|---|
| 顶部状态卡（点一下 / 长按） | GitHub：`owner/repo`、`branch`、`path`、PAT、发布私钥、镜像 | **一次**，粘贴 bootstrap JSON |
| 「+ 新增服务器」 | Emby：协议、host、port、账号、密码、别名 | 每台服务器一次 |
| 服务器卡片左侧复选框 | 本次要刷新/推送哪些服务器 | 每次操作前 |

勾选是**部分推送**：只把选中的服务器写进信封，母 APK 收到 `partial=true` 后
只更新这些条目，不会把未勾选的服务器从电视上删掉。

- 「全选 / 全不选」在列表上方，旁边显示「已选 N / M」。
- 每张服务器卡片有「上移 / 下移」。列表顺序会写进信封的 `orderIds`，母 APK 按它重写 `server_order`；**即使这次只勾选了一部分，未勾选服务器的位置也会按这个顺序调整**。
- 删除服务器仍然会随下次发布下发 `removedIds`，即使这次只勾选了别的服务器也会删掉它。
- 未勾选的服务器 token 不会被刷新；要更新它们就勾上再点「刷新选中」。
- 只推送一部分时，`lastUsedServerId` 不会强行改成第一台；电视本地当前使用的那台保持不变。
- **部分推送 + 排序要求电视端 `ChaiSync-tv 1.1+`（当前构建 1.2）。** 旧母 APK 不认识 `partial`/`orderIds`，会把未勾选的服务器当成“已从清单里删除”，从电视上删掉。升级顺序：先装新电视端，再用 1.0.7 子 APK 做部分推送。

「服务器」指的是 Emby 媒体服务器，**不是 GitHub 仓库**。App 不登录 GitHub、不自动发现仓库，也不读你电脑上的 git 配置——推到哪里完全由状态卡里那份 bootstrap 决定（`repo` / `branch` / `path` 三个字段）。

发布只做一件事：把 `path`（默认 `dist/config.enc.json`）这**一个文件** PUT 上去，仓库里其它文件不动。

发布前会显示 `seq 从 N → N+1`、服务器台数、目标仓库。确认后才推。

## 与旧 CF Worker 的功能对照

| Worker 的职责 | 子 APK 里对应什么 | 是否等价 |
|---|---|---|
| 存账号、下发 `/api/config` | 本地编辑 + 封装成信封推到 GitHub | ✅ |
| 刷新 Emby 令牌 | 「刷新选中」按钮，调 `AuthenticateByName` | ✅ 更安全：Emby 密码不再存在服务端数据库里 |
| 管理页增删改查 | 就是 App 界面本身 | ✅ |
| 设备白名单 / `HMAC(APP_SECRET, device_id)` | **没有** | ❌ 见下 |
| 定时刷新（Cron） | 没有；改成手动按 | ⚠️ 降级 |
| Cookie 会话、自定义域名 | 没有，也不需要 | — |

设备白名单在这里没有对应实现，不是漏了。那套东西的本质是**保护端点**；端点变成公开文件后，这层保护在语义上消失了，保护点整体转移到"密钥在谁手里"。所以：

> 放弃 Worker = 放弃按设备吊销。

剩下的手段只有：在 Emby 侧删设备、换 `kid`（重发一次即可，不用重打包）、换 `master`（要重打包）。自己用足够；要发给别人就得先想清楚这一条。

## 协议不变量（与母 APK 必须一致）

改这里的任何一条，都会让电视报 `bad-signature` 或 `bad-json`：

| 项 | 值 |
|---|---|
| 命名空间 | `chai-config-v1` |
| `v` / `alg` | `1` / `A256GCM+ES256` |
| base64 | **URL 安全字母表、无 padding** |
| 签名输入 | `NS\nv\nalg\nkid\nseq\niat\nn1\nwdek\nn2\nct`（逐字段换行，不是 JSON 原文） |
| KEK | `HKDF-SHA256(master, salt=kid, info="chai-config-kek-v1", 32)` |
| 包 DEK 的 AAD | `chai-config-v1\|wrap\|{kid}\|{seq}` |
| 正文的 AAD | `chai-config-v1\|payload\|{kid}\|{seq}` |
| GCM | 128-bit tag，密文与 tag 连在一起编码 |
| 正文里的 `seq` | 必须等于信封的 `seq`（母 APK 会查） |

`seq` 不是随手填的：发布前先 GET 远端信封、解出它的 seq，取 `max(本地, 远端) + 1`。这一步防的是"换手机后本地计数归零，推出一个小 seq，所有电视把它判成回滚并永久拒绝更新"——那个故障现象是**什么都不发生**，几乎无法排查。

## 测试

```bash
bash run-tests.sh
```

不需要设备、不需要模拟器、不联网。它用垫片提供 `android.util.Base64` / `android.net.Uri` / `org.json`，把**两边的真实源码**（本目录的 `EnvelopeSeal` 和 `../app/src` 的 `Envelope`）编进同一个 JVM 里跑，所以测的是真代码，不是复制品。

覆盖：

| 测什么 | 期望 |
|---|---|
| 子 APK 封装 → 母 APK 解封 | seq / servers / 中文 / 密码 全对 |
| 同上，交给 `tools/publish.mjs verify` | 三端同构 |
| 首次发布（远端 404） | seq=1，不带 sha |
| 二次发布 | seq=2，带 sha 覆盖 |
| 换手机（本地 0，远端 2） | seq=**3**，不是 1 |
| 改密文一个字符 | `bad-signature` |
| 重放旧 seq | `rollback` |
| 改 `kid` | `bad-signature` |
| 用错 master | `bad-master` |

## 已知限制

1. **只支持 PKCS#8 私钥**（`BEGIN PRIVATE KEY`）。`publish.mjs genkey` 产出的就是这个；如果是 `BEGIN EC PRIVATE KEY`（SEC1），导入会明确报错。
2. **`api.github.com` 在国内常不可达**。`apiBase` 可配成反代前缀，但很多反代只放行 GET，PUT 会被拒——用之前先测。最稳的做法还是手机上挂同一个代理。
3. **真机只验证到「导入」这一步**。用户实测时发现了 1.0 的 AndroidKeyStore IV 问题（1.0.1 已修）。刷新令牌、发布、电视端首屏这三段仍未在真机上完整走通。
4. **`allowBackup=false`**。这是故意的：Keystore 密钥不随备份迁移，备份出来的只是解不开的密文。
5. **不支持一台手机管多个仓库**。要换仓库就长按状态卡重新导入。多仓库场景下 `localSeq` 会互相污染。

## 版本与修复记录

| 版本 | 变更 |
|---|---|
| **1.0.7** | 服务器卡片增加「上移 / 下移」；信封带 `orderIds`，母 APK 按子 APK 的完整顺序重写 `server_order`，部分推送时未勾选项也能重排。 |
| **1.0.6** | 服务器卡片增加勾选；刷新/发布只处理选中项；信封带 `partial=true`，母 APK 不再把未勾选服务器删掉；删除走 `removedIds` 显式下发。 |
| **1.0.1** | 修 `caller-provided iv not permitted`。AndroidKeyStore 生成的 AES 密钥默认 `setRandomizedEncryptionRequired(true)`，此时 `Cipher.init(ENCRYPT_MODE, key, GCMParameterSpec)` 会抛 `InvalidKeyException` —— IV 必须由 Keystore 生成。`Vault.save` 改为两参数 `init` + `getIV()`。副标题现在显示版本号，方便确认装的是哪一版。 |
| 1.0 | 首个版本。**导入 bootstrap 必然失败**，别用。 |
