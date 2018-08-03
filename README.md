<h1 align="center">Readhub</h1>

[![Releases](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://www.coolapk.com/apk/com.jeez.guanpj.jreadhub)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

#### 此项目是个人基于 [Readhub](https://readhub.me) 开发一的一款非官方 Android 客户端，旨在为用户提供一个互联网资讯阅读平台和良好的阅读体验。

[![Google Play](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/google_play.png?raw=true)](https://play.google.com/store/apps/details?id=com.jeez.guanpj.jreadhub)

## 特点

- [x] 采用 Google Material Design 风格
- [x] 采用 MVP + dagger 2 架构进行解耦
- [x] 网络请求使用 Retrofit + RxJava
- [x] 本地数据库采用 Room + RxJava
- [x] 主题切换，支持夜间模式
- [x] 集成 Tinker 并支持应用内更新
- [x] 侧滑返回
- [x] 支持 CustomTabs
- [x] 新话题悬浮提示
- [x] 使用矢量图减少安装包体积

## 项目预览——夜间模式

| 起始页 | 主菜单 | 首页 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-splash.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-menu.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-main.png?raw=true) |

| 即时查看 | 话题详情 | 文章网页 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-instant.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-topic.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-article.png?raw=true) |

| 本地搜索 | 设置 | 关于 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-search.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-setting.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/dark-about.png?raw=true) |


## 项目预览——蓝色主题（默认）

| 起始页 | 主菜单 | 首页 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-splash.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-menu.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-main.png?raw=true) |

| 即时查看 | 话题详情 | 文章网页 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-instant.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-topic.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-article.png?raw=true) |

| 本地搜索 | 设置 | 关于 |
|:-:|:-:|:-:|
| ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-search.png?raw=true) | ![news](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-setting.png?raw=true) | ![drawer](https://raw.githubusercontent.com/guanpj/JReadhub/master/img/blue-about.png?raw=true) |

## 使用到的第三方开源库

项目名称 | 简介
  -------- | ------
[RxJava](https://github.com/ReactiveX/RxJava) | 一个专注于异步编程与控制可观察数据（或者事件）流的 API
[RxAndroid](https://github.com/ReactiveX/RxAndroid) | 在 Android 中使用 RxJava
[Retrofit](https://github.com/square/retrofit) | HTTP 请求工具库
[ButterKnife](https://github.com/JakeWharton/butterknife) | 强大的注解框架，提供视图和事件绑定
[Dagger](https://github.com/google/dagger) | 依赖注入框架
[Fragmentation](https://github.com/YoKeyword/Fragmentation) | 强大的 Fragment 管理库
[ExpandableLayout](https://github.com/cachapa/ExpandableLayout) | 支持展开和收缩子 View 的 Layout
[FlowLayout](https://github.com/hongyangAndroid/FlowLayout) | Android流式布局，支持单选、多选等，适合用于产品标签等
[leakcanary](https://github.com/square/leakcanary) | 使用于 Android 和 Java 和内存泄漏检查工具
[AgentWeb](https://github.com/Justson/AgentWeb) | Android WebView 框架
[Android-HeaderAndFooterRecyclerView](https://github.com/TakWolf/Android-HeaderAndFooterRecyclerView) | 支持添加头部和尾部的 RecyclerView
[Android-Debug-Database](https://github.com/amitshekhariitbhu/Android-Debug-Database) | 用于调试 Android SQLite 和 Shared preferences 的库
[Bugly](https://bugly.qq.com/v2/) | 帮助开发者快速发现并解决异常，同时掌握产品运营动态，及时跟进用户反馈
[Tinker](https://github.com/Tencent/tinker) | Android 热修复解决方案，支持 dex，library和资源更新且不用重新安装 app
[walle](https://github.com/Meituan-Dianping/walle) | Android Signature V2 Scheme签名下的新一代渠道包打包神器

## 使用到的工具/网站

网站名称 | 简介
  -------- | ------
[Iconfont](http://www.iconfont.cn/) | 阿里巴巴矢量图标库，提供了本项目中的大部分矢量图
[aconvert](https://www.aconvert.com/cn/image/resize/) | 在线调整图片大小（PNG, JPG 和 GIF）
[convertio](https://convertio.co/zh/png-converter/) | 在线图像文件转换器，支持 SVG 转换成 PNG
[logoko](http://www.logoko.com.cn/design) | 在线 logo 设计
[图帮主](http://www.tubangzhu.com/) | 在线平面设计
[shields](https://shields.io/) | metadata 图标设计
[compresspng](https://compresspng.com/zh/) | 在线压缩 PNG 图像

## 感谢

- [ReadHub](https://github.com/BryantPang/ReadHub)
- [Readhub-Android](https://github.com/TakWolf/Readhub-Android)
- [Awesome-WanAndroid](https://github.com/JsonChao/Awesome-WanAndroid)
- [Toutiao](https://github.com/iMeiji/Toutiao)

## License
> Copyright (C) 2018 guanpj.
> Licensed under the [GPL-3.0](https://www.gnu.org/licenses/gpl.html).
> (See the [LICENSE](https://github.com/guanpj/JReadhub/blob/master/LICENSE) file for the whole license text)
