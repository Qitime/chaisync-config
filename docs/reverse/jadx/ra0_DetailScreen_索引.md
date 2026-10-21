# `ra0.java` 索引：`DetailScreen.kt` 合并类

> `defpackage/ra0.java` 是 0.3.1-alpha3 详情页的全部 UI 逻辑。
> 4.4 MB / 60,847 行 / 950 个方法 / 112 处 `DetailScreen.kt` trace。
>
> 改详情页时**不要按类名找**，按下面的 `行号` 或界面中文文案搜索，
> 再用 jadx-gui 的交叉引用跳到调用点。

## 基本信息

| 项 | 值 |
|---|---|
| 混淆类 | `defpackage.ra0` |
| 原始源文件 | `com.dh.myembyapp.ui.screens.detail.DetailScreen.kt` |
| dex 位置 | `classes.dex` |
| 大小 / 行数 | 4.4 MB / 60,847 行 |
| 方法数 | 950 |
| Composable trace | 49 个（去重） |
| 无法反编译的方法 | 7 个（见文末） |
| 旧版对应物 | 0.2.6 分散在 44 个文件 / 合计 0.8 MB / 最大 535 KB |

## 定位方法

```bash
# 1. 按行号定位：找到 ra0 里对应的方法名
grep -n "DetailScreen.kt:1704" build/cmp-031/jadx031/sources/defpackage/ra0.java
#    输出里会带 com.dh.myembyapp.ui.screens.detail.DetailHeader，紧跟 `ra0.xxx(...)`

# 2. 按界面文案定位（中文在 dex 里是明文）
grep -n "详情页 Logo" build/cmp-031/jadx031/sources/defpackage/ra0.java

# 3. 在 jadx-gui 里打开，Ctrl+点击方法名看调用点（比命令行 grep 强）
```

## Composable 索引（按源码行号排序）

### 头部与主容器

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `DetailHeaderShadowIcon` | 1049 | 头部渐变遮罩图标 |
| `DetailScreen` | 1105 | 详情页根组件 |
| `DetailHeader` | 1704 | 头部（Logo、标题、背景图） |

### 剧集 / 季

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `EpisodeRail` | 1513 | 剧集横向列表 |
| `EpisodeRangeSelector` | 1581 | 剧集区间选择 |
| `SeriesHeaderPickerButtons` | 3299 | 剧集头部选择按钮组 |
| `SeasonPickerButton` | 3352 | 季选择按钮 |
| `SeasonSelectionDialog` | 3430 | 季选择弹窗 |
| `SeriesActionButtons` | 3187 | 剧集操作按钮组 |
| `EpisodePlayButton` | 9048 | 单集播放按钮 |
| `EpisodePlayDialog` | 8302 | 单集播放弹窗 |
| `EpisodeMiniActionButton` | 9208 | 单集小操作按钮 |
| `EpisodeInfoRow` | 9242 | 单集信息行 |
| `EpisodeVersionDialog` | 9314 | 单集版本弹窗 |

### 电影

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `MoviePlaybackOptionButtons` | 2323 | 电影播放选项按钮组 |
| `MoviePlaybackHeaderButton` | 2637 | 电影头部播放按钮 |
| `MovieVersionDialog` | 2691 | 电影版本弹窗 |
| `MoviePlaybackOptionsDialog` | 2724 | 电影播放选项弹窗 |
| `PrimaryActionButton` | 2937 | 主操作按钮 |
| `MovieActionButton` | 3093 | 电影操作按钮 |
| `LiveTvActionButton` | 3063 | 直播操作按钮 |

### 外部播放器

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `ExternalPlayerButton` | 4483 | 外部播放器入口 |
| `ExternalPlayerPickerDialog` | 4526 | 外部播放器选择弹窗 |
| `ExternalPlayerAppCard` | 4656 | 外部播放器应用卡片 |
| `ExternalPlayerErrorContent` | 4718 | 外部播放器错误提示 |

### Trakt

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `TraktWatchedSyncButton` | 4334 | Trakt 同步按钮 |
| `TraktWatchedSyncDialog` | 7108 | Trakt 同步弹窗 |
| `TraktWatchedSyncOptionButton` | 7259 | 同步选项按钮 |
| `TraktWatchedSyncStatusRow` | 7330 | 同步状态行 |

### 其他服务器资源

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `OtherServerResourcesDialog` | 3601 | 其他服务器资源弹窗 |
| `OtherServerStatusCard` | 3739 | 其他服务器状态卡片 |
| `OtherServerResourceRow` | 3769 | 其他服务器资源行 |
| `OtherServerVersionRail` | 3954 | 其他服务器版本横列 |
| `OtherServerVersionCard` | 4044 | 其他服务器版本卡片 |
| `OtherServerSearchButton` | 4294 | 其他服务器搜索按钮 |
| `RestartButton` | 4260 | 重启按钮 |

### 详情信息 / 元数据

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `DetailSupplementSection` | 7589 | 补充信息区块 |
| `DetailMediaSectionBlock` | 7609 | 媒体信息区块 |
| `DetailMetadataCardsRow` | 7655 | 元数据卡片行 |
| `DetailMetadataCard` | 7690 | 元数据卡片 |
| `DetailSupplementPanel` | 7772 | 补充信息面板 |
| `ContainingCollectionsSection` | 7426 | 所属合集区块 |
| `SimilarWorksSection` | 7507 | 相似作品区块 |
| `CastSection` | 9494 | 演员区块 |
| `CastCard` | 9554 | 演员卡片 |
| `InfoTag` | 9284 | 信息标签 |

### 操作按钮

| 原始 Composable | 行号 | 说明 |
|---|---|---|
| `MarkPlayedCircleButton` | 4375 | 标记已看 |
| `FavoriteButton` | 4415 | 收藏 |
| `SortOrderCircleButton` | 4770 | 排序切换 |

## 7 个无法反编译的方法

jadx 对 Compose 内联状态机的极限，只有签名，没有方法体：

| 方法 | 参数数 | 返回 | 推测用途 |
|---|---|---|---|
| `ra0.A` | 3 | Object | 协程状态机（`FocusRequester` + `MutableState`） |
| `ra0.L` | 31 | void | 详情页巨型 Composable（含 `VideoVersionPrioritySettings`） |
| `ra0.d` | **76** | void | 详情页主 Composable（最大，覆盖大部分 UI 状态） |
| `ra0.e` | 5 | Object | 滚动/焦点协程（`ScrollState` + `Rect`） |
| `ra0.f` | 7 | Object | 滚动/焦点协程（多 `MutableState`） |
| `ra0.k1` | 1 | String | 字符串处理 |
| `ra0.x1` | 1 | SubtitleLanguagePriorityType | 字幕语言优先级判定 |

**影响**：如果目标功能正好落在 `L` / `d` 里，jadx 给不了方法体，只能读 smali。
`x1` / `k1` 是纯逻辑，可以从调用点和字符串反推。

## 与 0.2.6 的对照

| | 0.2.6 | 0.3.1-alpha3 |
|---|---|---|
| 详情页相关文件 | 44 个（`y/`、`f0/`、`t/`、`w/`…） | 27 个（主要在 `defpackage`） |
| 合计体积 | 0.8 MB | **4.9 MB** |
| 最大单文件 | `y/f2.java` 535 KB / 29 trace | **`ra0.java` 4.4 MB / 112 trace** |
| 最大单类方法数 | — | **950** |

旧版每个组件是独立小类，改哪个打开哪个；新版糊成一个类，这是"难改"的直接来源。
