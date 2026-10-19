# ChaiSync-GH

柴柴 Emby TV（`com.dh.myembyapp` 0.3.1-alpha3）的冷启动账号同步，**不依赖任何自有服务器**。

配置放 GitHub 公开仓库（密文信封），客户端经镜像加速拉取、验签、解密、写入 prefs，
在 `MainActivity.onCreate` 之前生效——改完账号重开一次电视就生效，不用重开两次。

> 替代掉的是旧项目里的 Cloudflare Worker + D1 + 自定义域名。
> 那套的不稳定源是 TLS SNI 干扰（`emby.qiqitao.me` 成功率 25%–70%），不是逻辑问题；
> 换成三个独立镜像 fallback 之后这个问题从根上消失。

---

## 它是什么 / 不是什么

**是**：一条"改账号 → 推到 GitHub → 电视下次开机生效"的单向通道。

**不是**：
- 不是多设备管理后台。没有设备白名单、没有按设备吊销、没有在线管理页。
- 不是机密分发系统。APK 只给信任的人，密钥随之分发——所以威胁模型是
  「**别人看见了也解不开**」，不是「**拿到 APK 也解不开**」。区别见下面 §安全模型。
- 不含 APK 本身。这个仓库只有补丁源码与发布工具，原包自己准备。

> **另有一个可选的手机端**（[`admin/`](admin/README.md)）：装在自己手机上的子 APK，
> 带界面管服务器、刷令牌、一键加密推到 GitHub，不用每次开电脑改 JSON。
> 不装也能用——本目录的命令行工具就是完整链路。

---

## 快速开始

### 1. 生成密钥（只做一次）

```bash
node tools/publish.mjs genkey --out keys
```

记下输出的两个值，后面构建要用：`PUBKEY_HEX`（公钥，钉进 APK）、`MASTER_B64`（主密钥）。

`keys/signing.pem` 是签名私钥，**绝不能入库**（`.gitignore` 已排除）。

### 2. 写明文配置

```bash
cp config/config.example.json config/config.json
```

改 `servers[]` 里的 `host / port / path / username / password`。
`id` 必须唯一且非空——缺了会被 Gson 解析成一行空白条目。

### 3. 刷令牌并发布

```bash
node tools/publish.mjs refresh --in config/config.json --out dist/config.enc.json \
     --key keys/signing.pem --master "<MASTER_B64>"
```

`refresh` 会调 Emby 的 `Users/AuthenticateByName` 把 `userId` / `accessToken` 填好，
然后加密签名输出到 `dist/config.enc.json`。`seq` 自动递增，不用手填。

只想发布现有明文（不联网刷令牌）：

```bash
node tools/publish.mjs publish --in config/config.json --out dist/config.enc.json \
     --key keys/signing.pem --master "<MASTER_B64>"
```

### 4. 推到仓库

```bash
git add dist/config.enc.json && git commit -m "config: seq N" && git push
```

公开仓库即可。仓库里只有密文，凭据是加密的（`refresh` 后目录里只有 `dist/` 进版本库）。

### 5. 打包 APK

```powershell
powershell -ExecutionPolicy Bypass -File .\build\build.ps1 `
  -MasterB64 "<MASTER_B64>" `
  -PubKeyHex "<PUBKEY_HEX>" `
  -RawBase   "https://raw.githubusercontent.com/<你>/<仓库>/main/dist/config.enc.json"
```

产物 `build/chaichai-sync.apk`。装之前必须先卸载原版（重签名会变，数据会被清空）。

---

## 日常操作

| 场景 | 做什么 |
|---|---|
| 改账号 / 加账号 | 改 `config/config.json` → 跑 `publish` → push |
| token 过期（电视提示登录） | 跑 `refresh` → push |
| 换服务器地址 | 改 `host` → 跑 `publish` → push |
| 换主密钥 | `genkey --out keys2` → 重新 `publish` → **重打包 APK**（主密钥在 APK 里） |
| 全自动（可选） | 启用 `.github/workflows/refresh-token.yml`，见文件头注释里的三个 Secrets |

发布之后**最多 5 分钟**才会全网可见（raw 有 `Cache-Control: max-age=300`）。这不是失败，
客户端检测到 `seq` 不前进时会自动换缓存键重问一次，**不要反复重发**。

---

## 目录

```
app/src/com/dh/myembyapp/sync/
  SyncProvider.java   入口。ContentProvider.onCreate 早于 Application/Activity，这是"同一次启动生效"的前提
  Envelope.java       验签 + 解密 + 防回滚。唯一承载安全的部分
  Fetcher.java        单 URL 抓取（线程 + join 硬超时），镜像地址拼装
  Probe.java          探测当前服务器是否还活着 + sync_status.txt 落盘
  Store.java          明文配置读取、prefs 合并写入、信封缓存

tools/publish.mjs     genkey / publish / refresh / verify（零依赖，只用 Node 内置 crypto）
tools/bootstrap.mjs   生成给手机端导入的 bootstrap JSON（含私钥，粘一次就删）
tools/make-bootstrap-page.mjs  把 bootstrap JSON 变成可一键复制的一页 HTML
tools/precommit.sh    拦住明文凭据进 git（强烈建议装）
build/build.ps1       重打包：只替换 manifest + assets，追加一个 classes6.dex，其余字节原样
build/splice.py       拼包脚本：原 APK 全量保留，.so 保持 uncompressed + 页对齐

admin/                手机端发布 App（可选，见 admin/README.md）
  src/…/EnvelopeSeal.java  封装端；与母 APK 的 Envelope 字节同构
  src/…/Publisher.java     发布编排：读远端 → 定 seq → 封装 → 自检 → 推
  src/…/Vault.java         AndroidKeyStore 加密的本地保险库（私钥不编进 APK）
  build-apk.sh             零 Gradle 构建（aapt2 + javac + d8 + apksigner）
  run-tests.sh             用两端真实源码做的协议回归测试

docs/reverse/               母 APK 顶栏 / 服务器切换 / 排序逻辑的反编译归档
dist/config.enc.json         发布产物（入库）
dist/config.enc.example.json 示例产物（假账号，用来验证本地链路是否通）
config/config.json           明文配置（不入库）
```

**先自测一下链路**（不联网、不改任何东西，只验证密钥对得上）：

```bash
node tools/publish.mjs verify --in dist/config.enc.example.json \
     --pub keys/pubkey.hex --master "$(cat keys/master.b64)"
```

能解出 `servers=2` 就说明密钥、算法、编码全都对；报 `签名不匹配` 说明公钥和私钥不是一对。

---

## 手机端（子 APK，可选）

[`admin/`](admin/README.md) 是一个独立的 Android App，装在自己手机上，替代"开电脑改 JSON"。

| 场景 | 电脑（本目录） | 手机（`admin/`） |
|---|---|---|
| 改账号 | 编辑 `config.json` → `publish` → push | App 里改 → 点「发布」 |
| 刷令牌 | `node tools/publish.mjs refresh` | 勾选后点「刷新选中」 |
| 首次配置 | `genkey` + 写 config | 粘贴一次 bootstrap JSON |

关键区别只有一条：**发布私钥不在 APK 里**。母 APK 放 `master` 是安全的（只能解、不能伪造），
而子 APK 要签发信封就必须有私钥——编进包等于任何拆包的人都能发出你的电视会接受的信封。
所以它走"首次导入 + AndroidKeyStore 加密存盘"，代价是换手机不能靠备份迁移。

```bash
bash admin/build-apk.sh     # → admin/dist/ChaiSync-admin-1.0.7.apk（53 KiB，零 Gradle）
bash admin/run-tests.sh     # 协议回归测试，不需要设备
```

详细说明、协议不变量、测试覆盖见 [`admin/README.md`](admin/README.md)。

---

## 工作原理

```
首次运行（从没同步成功过）      → 阻塞拉取（≤9s）→ 写入 → 进入
已同步过 → 先应用待生效缓存（0ms）
    ├ 探测当前服务器通过        → 直接进入；后台顺手拉一次（只更新缓存，下次启动才用）
    └ 探测失败（换地址/token 失效）→ 阻塞拉取 → 立即写入 → 同一屏用新配置进入
拉取失败（镜像全挂 / 信封被拒）  → 保留本地配置进入，原因写 sync_status.txt
```

探测优先是省流量的关键：**服务器好好的时候完全不碰 GitHub**。
所以你日常开机不会因为镜像抖动而不稳，只有真出问题时才走网络。

拉取时按 `mirrors` 顺序逐个试，每个响应都过一遍 `Envelope.open()`：
验签失败或被判 rollback 就换下一个源，全挂才保留本地。

---

## 电视端：沿用原 App 切换 + 启动故障转移

母 APK 顶栏本来最左侧就有 `ServerButton`（服务器图标 + 名字），右边是
媒体 / 收藏 / 搜索 / 设置；点它打开原 App 的「切换服务器」弹窗。本补丁
**不叠加圆点，也不改顶栏布局**，只把子 APK 的排序结果写进 `server_order`。

**启动故障转移**：`SyncProvider` 探测当前服务器失败（TCP 不通 / token 无效）时，
会按 `server_order` 顺序尝试后面的服务器，最多 3.5 秒；第一台能用就写 `last_used_server`，
首屏直接用它。拉取新配置失败、只能留本地时也会走这一步。健康启动不会触发，也不会多花时间。
运行中掉线不会自动切；下次冷启动才会。

> **兼容性**：子 APK 1.0.7 的「部分推送 + 排序」依赖母 APK 1.1+ 的
> `partial` / `orderIds` 语义（当前构建 1.2）。旧母 APK 会把未勾选的服务器当成“已删除”。
> **先升级电视端，再用子 APK 做部分推送。**

---

## 安全模型

三层各管一件事，**缺一层破一个口子，不要用其中一层去顶另外两层**：

| 层 | 管什么 | 挡住谁 |
|---|---|---|
| AES-256-GCM | 别让人**看见** | 镜像站、公开库爬虫、secret scanning、看仓库的人 |
| ECDSA-P256-SHA256 | 别让人**换掉** | 恶意镜像、仓库被改、别人 fork |
| `seq` 单调递增 | 别让人**退回旧版** | 重放攻击（旧信封签名也合法） |

真实威胁池，按**发生概率**排（不是按严重程度）：

| 风险 | 概率 | 挡得住吗 |
|---|---|---|
| 你自己把明文 `config.json` 提交进 git | 高 | ❌ 加密管不到，靠 `tools/precommit.sh` |
| 公开仓库 Actions 日志对所有人可见 | 中 | ❌ 靠写法约束，别开 `set -x`、别 echo 凭据 |
| 拿到 APK 的人把 APK 转发出去 | 中 | ❌ 信任不可传递，只能在 Emby 侧吊销 |
| 爬虫 / secret scanning 命中 | 中 | ✅ 密文免疫 |
| 镜像站记录或篡改响应 | 低但不可审计 | ✅ 加密 + 签名 |
| 有人专门逆向 APK 取密钥 | 极低 | ❌ 挡不住（密钥必须随 APK 到设备） |

装 pre-commit 钩子：

```bash
cp tools/precommit.sh .git/hooks/pre-commit && chmod +x .git/hooks/pre-commit
```

---

## 排错

电视上没有可用的 logcat，所有判断都写进：

```
/sdcard/Android/data/com.dh.myembyapp/files/sync_status.txt
```

```bash
adb shell cat /sdcard/Android/data/com.dh.myembyapp/files/sync_status.txt
```

原因码对照：

| 码 | 含义 | 处理 |
|---|---|---|
| `probe ok (...) -> fast path` | 正常，没碰网络 | — |
| `probe failed (tcp-fail...)` | 服务器换了/挂了/端口被封 | 正常流程，会去拉新配置 |
| `probe failed (token-invalid:401)` | token 失效 | 跑 `refresh` 重新发布 |
| `verified via=... seq=N` | 成功 | — |
| `bad-signature` | 签名不对：镜像篡改，或 APK 里的公钥与发布端不匹配 | 核对 `-PubKeyHex` 与 `keys/pubkey.hex` |
| `bad-master` | 主密钥不对 | 核对 `-MasterB64` |
| `rollback` | 拿到的 seq 不比本地新（通常是 CDN 缓存还没刷新） | 等 5 分钟；持续发生说明 `seq` 没递增 |
| `bad-json` | 拿到 HTML 错误页或被截断 | 换镜像；检查 `RawBase` 拼写 |
| `fetch-failed \|...` | 所有镜像都失败 | 看后面跟的每一条 |
| `budget-exhausted` | 9 秒预算用光 | 正常降级，用本地配置进入 |
| `config incomplete` | 打包时参数没传全 | 重跑 `build.ps1` |

---

## 已知限制

1. **没有按设备吊销**。端点公开了，保护点从"设备身份"移到"密钥"。要收回权限只能：
   去 Emby 后台删设备 / 改密码，或者换主密钥重发 APK。
2. **主密钥换不了热**。换密钥必须重打包 APK，因为密钥在 APK 里。
3. **`seq` 只增不减**。如果要重置（比如换仓库重建），客户端得先清数据（`adb shell pm clear com.dh.myembyapp`）。
4. **镜像域名会死**。ghproxy 系列历史上换过好几轮。`mirrors` 是编进 APK 的，死了要重打包。
   缓解办法：多放几个；或者让信封正文里也带一份 `mirrors`，客户端每次成功拉取后自我更新。
5. **打包链路未在本机端到端跑过**。`apktool / d8 / zipalign / apksigner` 沿用旧项目已验证的流程，
   但这一版没有实机验证；第一次跑请留意 `[4/7] javac` 与 `[5/7] splice` 两处的输出。
6. **故障转移只在启动时做一次**。运行中服务器掉线不会自动切；下次冷启动才会。
7. **排序通过 `orderIds` 重写 `server_order`**。子 APK 每次发布都会按自己的列表顺序覆盖电视上的排序；当前选择中的服务器仍由原 App 本地优先，不会被推送强行改掉。

---

## 环境依赖

| 用途 | 路径 |
|---|---|
| 原包 | `C:\Users\qiqi\Downloads\ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk` |
| apktool | `D:\Ai\tools\apkrev-tools\apktool\apktool.jar` |
| build-tools | `D:\Ai\Android\sdk\build-tools\37.0.0` |
| android.jar | `D:\Ai\Android\sdk\platforms\android-36\android.jar` |
| JDK | `D:\Ai\Android\JDK`（21） |
| Python | 3.13（`splice.py`） |
| adb | `D:\Tv\QtScrcpy-win-x64-v3.3.3\adb.exe` |

Node 22+（`publish.mjs` 用了内置 `fetch` 与 `AbortSignal.timeout`）。
