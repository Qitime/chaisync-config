# 柴柴 Emby TV 0.3.1-alpha3 反编译归档

归档目的：把母 APK 顶栏、服务器切换、服务器排序相关的反编译结果留在仓库里，
以后改注入代码时不用每次重新拆包。

> **当前仓库状态**：代码已回退到 1.0 基线（commit `4ae1648`）。
> 本目录只保留分析，不包含之前实验性的顶栏圆点、部分推送、启动故障转移等改动。

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

### 3.3 子 APK 推送能不能控制排序（1.0 基线）

1.0 只有“全量发布”，所以答案是**能，而且列表顺序和默认服务器都能控制**：

| 信封字段 | 母 APK 写入的 prefs | 效果 |
|---|---|---|
| `servers` 数组顺序 | `server_order` = 这个顺序，后面补本地剩余 | 决定切换弹窗里的服务器顺序；第一个就是“排第一” |
| `lastUsedServerId` | `last_used_server` | 决定 App 启动时加载哪一台 |

子 APK 1.0.5 的 `Publisher` 里：

```java
// servers 按本地 state 的数组顺序写进信封
clean.put(normalize(s));
// last = state.lastUsedServerId，若不在列表里则取第一台
payload.put("lastUsedServerId", last);
```

所以推送会**覆盖**电视上的手动排序和当前选择：

- 想让某台排第一：在子 APK 的 `servers` 数组里把它放最前（当前 UI 没有上移/下移，
  只能通过添加顺序或直接改 `vault.bin` 实现）。
- 想让某台成为启动默认：把 `state.lastUsedServerId` 指向它（当前 UI 也没有“设为默认”按钮）。
- 每次发布都会把电视上手动排好的 `server_order` 和 `last_used_server` 重置为信封里的值。

如果希望“推送只更新账号、不改电视排序”，需要改协议/Store 加显式开关，
不能靠 `servers` 顺序隐式表达——否则每次发布都会悄悄覆盖用户的手动排序。

---

## 4. 失败时会不会自动切换

### 原 App 自身（1.0 基线）

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

所以 1.0 基线的答案是：**不会自动换服务器**。当前服务器连不上时，
只会停在这台的错误/未登录流程，需要用户自己打开服务器切换弹窗选另一台。

### 之前实验过、已回退的 failover

之前那版补丁在 `SyncProvider` 里加过 `Probe.failover()`：冷启动时如果当前服务器
TCP/token 探测失败，就按 `server_order` 顺序尝试后面的服务器，3.5 秒预算，成功就写
`last_used_server`。这段代码已随 1.0 基线回退删除。

如果以后要恢复，建议做成独立开关，并明确：
- 只在冷启动做一次，运行中掉线不切；
- 不自动切回第一台；
- 在 `sync_status.txt` 里记录每次尝试和最终选择。

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
