# 柴柴 Emby TV 0.3.1-alpha3 反编译归档

归档目的：把母 APK 顶栏、服务器切换、服务器排序相关的反编译结果留在仓库里，
以后改注入代码时不用每次重新拆包。

> **当前仓库状态**：子 APK 1.0.7 + 母 APK 1.2。
> 保留勾选/部分推送、`orderIds` 排序、启动故障转移；**不再包含顶栏圆点**。

- 源 APK：`C:\Users\qiqi\Downloads\ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk`
- 反编译对象：`build/decoded/classes4.dex`（UI / 业务逻辑主要在 classes4）
- 工具：jadx 1.5.6，`--no-res`
- 归档文件：`docs/reverse/jadx/`，文件名后缀标了原始类名与用途

复现命令：

```bash
# 1. 解包（只解 manifest / resources，跳过 smali）
java -jar D:/Ai/tools/apkrev-tools/apktool/apktool.jar d -f -s \
  -o build/decoded "C:/Users/qiqi/Downloads/ChaiChaiEmbyTV-0.3.1-alpha3-arm64-v8a.apk"

# 2. 反编译 classes4（约 2 分钟）
D:/Ai/tools/apkrev-tools/jadx/bin/jadx.bat \
  -d build/jadx4 --no-res -j 4 build/decoded/classes4.dex
```

> 反编译代码里的 `defpackage.ya2`、`hr0`、`so0` 是混淆名；
> 文件里的 `ComposerKt.traceEventStart(... "xxx.kt:line")` 保留了原始源码文件名和行号，
> 这是定位 UI 逻辑最可靠的线索。

---

## 1. 顶栏结构

原始源码位置：`com.dh.myembyapp.ui.screens.home.TopNavigation.kt`

| 组件 | 混淆类 | 原始位置 | 尺寸 / 说明 |
|---|---|---|---|
| TopNavigation | `ya2.d` | TopNavigation.kt:91 | 高 72dp，左右 padding 48dp，子项间距 12dp |
| ServerButton | `ya2.b` | TopNavigation.kt:453 | 高 42dp，宽 `widthIn(85dp, 250dp)`，图标 + 服务器名 |
| NavButton | `ya2.a` | TopNavigation.kt:310 | 85×40dp，图标 + 文字 |
| TopNavIconButton | `ya2.c` | TopNavigation.kt:634 | 40×40dp（设置按钮） |

顶栏布局（`ya2.d` 约 2040–2093 行）：

```
Row(padding 48dp, spacing 12dp)
├─ ServerButton(str=服务器名, str2=图标URL, num=图标资源)
├─ [可选] 代理状态图标 24dp
├─ Spacer(weight=1)
└─ Row(spacing 12dp)
   ├─ NavButton("媒体")
   ├─ NavButton("收藏")
   ├─ NavButton("搜索")
   └─ TopNavIconButton(设置)
```

关键尺寸常量在 `ya2.java:76-77`：

```java
public static final float a = Dp.constructor-impl(85.0f);   // 最小宽度
public static final float b = Dp.constructor-impl(250.0f);  // 最大宽度
```

`ServerButton` 的内容在 `x52.java`（`ServerButton.<anonymous> (TopNavigation.kt:583)`）：
34dp 图标 + 6dp 间距 + 服务器名 + 4dp 间距，按钮内部 padding 10dp/3dp。

**结论：原 App 顶栏本来就有服务器入口。** 不需要另外加圆点或浮动控件；
切换入口就是最左侧的 ServerButton。

---

## 2. 切换服务器弹窗

首页 `hr0.java` 里有两处相同调用（约 5488 / 8401 行）：

```java
so0.d("切换服务器", listA, true, function43, (Function0) objRememberedValue105, ...);
```

`so0.d` 是 `HomeRouteSwitchDialog (HomeMediaMenuDialog.kt:250)`：
一个通用的“列表选择”对话框，标题由调用方传入，这里传的是「切换服务器」。

列表由 `r02.a(...)` 构造（`r02.java`）：

```java
list.add(new vo0("__server_list__", "返回服务器列表页", ...));
if (z) list.add(new vo0("aggregate_search", "聚合视界", ...));
for (ServerConfig server : servers) {
    list.add(new vo0(
        server.getId(),
        server.getAlias(),
        displayAddress + routeSuffix + (isLoggedIn ? "" : " · 需重新登录"),
        !z2 && id.equals(lastUsedServerId),   // 当前服务器标记
        ...
    ));
}
```

- 列表顺序 = 传入的 `getAllServers()` 顺序。
- 当前服务器只是加“当前”标记，**不会自动挪到第一行**。
- 列表第一行是「返回服务器列表页」，第二行可能是「聚合视界」；
  第一台真正服务器在它们之后。

---

## 3. 服务器排序逻辑

实现：`ServerPreferences.java`

### 3.1 读取顺序（`getAllServers`）

```java
List<ServerConfig> storedServers = readStoredServers();
List<String> order = readStoredServerOrder();

if (order == null || order.isEmpty()) {
    // 没有 server_order：按 lastLoginTime 降序
    return sortDescByLastLoginTime(storedServers);
}

// 有 server_order：先按 order 排列
for (String id : order) { ... }

// order 里没有的服务器：追加，并按 lastLoginTime 降序
return plus(ordered, sortDescByLastLoginTime(remaining));
```

所以「排第一」的规则是：

1. `server_order[0]` 存在且对应服务器存在 → 它排第一。
2. `server_order` 缺失/为空 → `lastLoginTime` 最大的服务器排第一。
3. `server_order` 里没有的新服务器 → 追加到末尾，且新服务器之间按 `lastLoginTime` 降序。

注意：`last_used_server`（当前正在用的服务器）**不参与排序**。
它只决定启动时加载哪一台，列表里只加“当前”标记。

### 3.2 写入顺序

- `saveServerOrder(List<String>)`：ServerSortDialog 手动排序后调用；
  内部用 `buildStoredOrderWithAllServerIds` 补齐缺失 id，过滤不存在的 id，去重。
- `saveServer(config)`：如果已有 `server_order`，会用当前 `servers` 数组顺序重建 order。
- `buildStoredOrderWithAllServerIds`：保留 order 中仍存在的 id，其余 id 按传入的
  `allServerIds`（`servers` 数组）顺序追加。

### 3.3 子 APK 推送怎么控制排序（当前 1.0.7 / 母 1.2）

母 APK 的 `getAllServers()` 只认 `server_order`。所以子 APK 排序不能只靠
`servers` 数组顺序——部分推送时未勾选的服务器不在 `servers` 里。当前实现额外发
一个 `orderIds`：

| 信封字段 | 母 APK 写入的 prefs | 效果 |
|---|---|---|
| `orderIds` | `server_order` = 这个顺序（后面补 result 里剩余的 id） | 决定切换弹窗里的完整服务器顺序；`orderIds[0]` 就是“排第一” |
| `servers` | 各服务器条目 | 只决定本次更新哪些服务器，不再单独决定顺序 |
| `lastUsedServerId` | `last_used_server` | 只在电视本地没有有效选择时生效；已有本地选择优先 |

子 APK 1.0.7 的 `Publisher`：

```java
// orderIds 包含所有服务器（含未勾选），按子 APK 列表顺序
orderIds.put(id);
payload.put("orderIds", orderIds);
```

母 APK `Store.apply` 在 `partial` 合并完成后，用 `orderIds` 重写 `server_order`，
过滤掉已经删除/不存在的 id，再把 result 里没出现的 id 追加到末尾。这样：

- 子 APK 里点「上移/下移」→ 发布后电视切换弹窗的顺序跟着变。
- 只勾选一部分更新时，未勾选服务器的位置也会按 `orderIds` 调整，但配置不被改写。
- 当前正在使用的服务器仍由电视本地 `last_used_server` 优先，推送不会强行换服。
- 旧母 APK（不认识 `partial`/`orderIds`）会把未勾选项误删，必须先升级电视端。

---

## 4. 失败时会不会自动切换

### 原 App 自身

`MainActivity.onCreate`（旧项目 `05-internals/MainActivity.java:45-55`）只做：

```java
ServerConfig lastUsedServer = new ServerPreferences(this).getLastUsedServer();
if (lastUsedServer == null || !effectiveServerConfig.isLoggedIn()) {
    retrofitClient.setAuthToken("", "");
    // 进入未登录/服务器列表流程
}
```

它会加载 `last_used_server` 指向的那台；这台不可用时**没有跨服务器轮询**。
每台服务器自己的 `backupRoutes` 是“同一台服务器的备用线路”，不是服务器之间的 failover。

### 当前补丁恢复的 failover

`app/src/com/dh/myembyapp/sync/Probe.java` 的 `failover()`，由 `SyncProvider.onCreate` 调用：

- 触发时机：首次运行拉取后，或当前服务器 probe 失败后。
- 顺序：`server_order` 顺序，从当前服务器之后开始循环尝试。
- 单台判断：TCP 连通 + `GET /Users/Me` 返回 200；`not-logged-in` / `token-invalid` 跳过。
- 预算：总 3.5 秒（`700ms TCP + 1000ms HTTP`，受剩余预算约束）。
- 成功：写 `last_used_server`，首屏直接用新服务器。
- 失败：保留原 `last_used_server`，不阻塞进入 App。
- **只在冷启动阶段做一次**；App 运行中服务器掉线不会自动切；也不会自动切回第一台。

---

## 5. 归档文件对照

| 文件 | 原始类 | 用途 |
|---|---|---|
| `jadx/ya2_TopNavigation.java` | `defpackage.ya2` | TopNavigation / ServerButton / NavButton / TopNavIconButton |
| `jadx/x52_ServerButtonContent.java` | `defpackage.x52` | ServerButton 内部图标、服务器名布局 |
| `jadx/hr0_HomeScreen.java` | `defpackage.hr0` | 首页；顶栏调用、切换服务器弹窗、排序入口 |
| `jadx/so0_HomeRouteSwitchDialog.java` | `defpackage.so0` | 通用列表选择对话框（切换服务器/线路） |
| `jadx/r02_ServerListRows.java` | `defpackage.r02` | 构造切换服务器列表行，当前项标记 |
| `jadx/x5_ServerSortDialog.java` | `defpackage.x5` | 服务器排序弹窗 |
| `jadx/ServerPreferences.java` | `com.dh.myembyapp.data.preferences.ServerPreferences` | 服务器存取、`server_order`、`last_used_server` |
| `jadx/ServerConfig.java` | `com.dh.myembyapp.data.model.ServerConfig` | 服务器数据模型（含 deviceId / token / backupRoutes） |
