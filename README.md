# 扶摇 bot
一个基于 [Mirai](https://github.com/mamoe/mirai) 、Spring Boot、MySQL 和 MyBatis Plus 的 QQ 机器人。也算是用学校学的点东西做的练手小玩具。

# 目录

- [操作列表](#操作列表)
  * [群内相关事件提醒](#群内相关事件提醒)
  * [签到和金币](#签到和金币)
  * [游戏](#游戏)
  * [查询](#查询)
- [TO-DO](#TO-DO)
- [更新日志](#更新日志)

# 操作列表
*<p align="right">最后更新时间：2021-09-11</p>*
*<p align="right">对应版本：4.5.3.69 及以上</p>*

**使用功能前请在最前方加入~或/符号，例如“~签到”或“/签到”。**

### 群内相关事件

|  序号  |  监听类型  |  是否自动撤回  |
|  :----:  |  :----:  |  :----:  |
|  1  |  群员进群 / 退群 / 被踢 / 被禁言 / 解除禁言 / 设为管理员 / 取消  |  1 分钟后<br>自动撤回  |
|  2  |  群荣誉事件  |  否  |
|  3  |  群成员获得新头衔  |  否  |
|  4  |  群内成员发闪照  |  否  |
|  5  |  群内成员发红包  |  否  |
|  6  |  机器人群名片被修改后自动复原  |  否  |
|  7  |  用户触发 10s 内防刷屏机制  |  冷却时间<br>结束时撤回  |
|  8  |  每日零点发送群内消息数量统计  |  否  |
|  9  |  7:30 早上好  |  否  |
|  10  |  群名片修改提醒  |  1 分钟后<br>自动撤回  |
|  11  |  群名修改提醒  |  否  |

### 签到和金币

|  序号   | 关键字  |  功能  |
|  :----:  | :----:  |  :----: |
|  1  | 签到 |  发送签到，获取经验值  |
|  2  | EXP查询 / 经验查询  |  签到经验值查询,发送EXP查询，获取本人在该群的经验值  |
|  3  |  领金币  |  发送领金币，获取金币  |
|  4  |  金币查询  |  金币查询,发送金币查询，获取本群的经验值  |
|  5  |  金币转移  |  发送“金币转移 数量 @接收人”，可以将金币转移给他人  |

### 游戏

##### 钓鱼

|  序号  |  关键字  |  功能  |
|  :----:  | :----:  |  :----: |
|  1  |  钓鱼  |  发送“钓鱼”，就可以钓一条鱼  |
|  2  |  鱼种查询  |  发送“鱼种查询”  |
|  3  |  鱼筐查询 / 鱼框状态  |  查看鱼筐状态  |
|  4  |  福利金币  |  领取福利金币  |
|  5  |  卖鱼  |  卖鱼  |

##### 抽卡

|  序号  | 关键字  |  功能  |
|  :----:  |  :----:  |  :----:  |
|  1  |  炉石传说或hs  |  炉石传说最新扩展包卡牌的开包  |

##### 其它游戏

|  序号  | 关键字  |  功能  |
|  :----:  |  :----:  |  :----:  |
|  1  |  投骰子或roll dice  |  投骰子  |
|  2  |  石头剪刀布或rps  |  石头剪刀布  |

### 查询
|  序号  |  关键字  |  功能  |
|  :----:  |  :----:  |  :----:  |
|  1  |  守望街机模式或ow mode  |  守望先锋当日街机模式查询  |
|  2  |  时间或time  |  当前时间，包含农历  |
|  3  |  music + 歌名  |  QQ音乐点歌  |
|  4  |  wtf + 需要查询的缩写  |  能不能好好说话（缩写转全称）  |
|  5  |  group settings  |  群内设置查询  |
|  6  |  bot staus  |  扶摇 bot 状态查询  |

### 使用反馈或建议
如果有使用反馈或建议请发送 "feedback + '需要反馈的意见，可在消息中附上图片'"（必须将消息与图片一起发送，分为两条消息发送将无法收到图片）。
示例："/feedback反馈 添加人工智能回复"

# TO-DO
* Web前端管理
* Redis缓存
* 功能分群单独开关

# 更新日志
## 4.5.3.69 (2021-09-11)
* **更新** 部分依赖
* **新增** 群员被禁言提醒、被解禁提醒
* **新增** 群名修改提醒
* **新增** 扶摇 bot 的建议与反馈
* **优化** 部分文案
* **优化** 扶摇 bot 群名片被修改后的判断逻辑
* **优化** 群消息和好友消息的判断逻辑

## 4.5.2.68 (2021-09-10)
* **更新** Mirai 2.7.1-dev-1
* **新增** 群内设置查询
* **新增** 扶摇 bot 状态查询
* **优化** 部分文案
* **修复** 守望先锋模式查询 API

<details>
<summary>4.0.0 +</summary>

## 4.5.1.67 (2021-08-31)
* **新增** 私聊也可以触发 bot 的（部分）功能
* **优化** 提醒文案

## 4.5.0.66 Hotfix (2021-08-30)
* **优化** 部分提醒文案
* **优化** 定时消息发送的发送时间
* **优化** 群内发送消息逻辑
* **优化** README文件

## 4.5.0.66 (2021-08-29)
* **优化** 部分提醒文案
* **优化** 定时消息发送
* **优化** 群内发送消息逻辑
* **更新** Mirai 2.7.0

## 4.4.9.65 Hotfix (2021-08-20)
* **修复** HashMap遍历时的线程同步问题

## 4.4.9.65 (2021-08-20)
* **更新** 更新依赖
* **新增** 群内消息计数器
* **移除** 移除新好友和新群计数器
* **优化** 修改早间提醒的发送机制
* **优化** RSS抓取

## 4.4.8.64 (2021-08-15)
* **更新** 更新为Mirai 2.7-RC
* **优化** 复读消息判断
* **优化** 红包和视频消息判断
* **新增** bot群名片被修改后自动改回

## 4.4.7.63 (2021-08-14)
* **修复** 非文本消息复读
* **优化** 红包、视频消息判断
* **优化** RSS订阅消息发送

## 4.4.6.62 (2021-07-27)
* **新增** 当用户撤回触发机器人的消息后机器人也会撤回发送的消息（防止部分别有用心的用户做一些奇怪的事情）
* **优化**  复读逻辑
* **优化**  代码冗余应用
* **优化**  全局变量存放位置

## 4.4.5.61 (2021-07-25)
* **优化**  RSS消息提醒判断逻辑
* **优化**  数据库SQL脚本
* **优化**  分离开发环境和测试环境的配置文件
* **优化**  错误提醒日志

## 4.4.4.60 (2021-07-23)
* **新增** RSS消息提醒
* **新增** RSS消息提醒对应的数据库SQL
* **优化** 分离开发环境和测试环境的配置文件

## 4.4.3.59 (2021-07-22)
* **更新** 更新为Mirai 2.7-M2
* **更新** 适配Mirai新功能

## 4.4.2.58 (2021-06-03)
* **更新** 更新为Mirai 2.6.5
* **删除** 人工智障回复（过于智障）

## 4.4.1.57 (2021-05-12)
* **更新** 更新为Mirai 2.6.4
* **新增** 人工智障回复
* **新增** 防刷屏
* **修复** 获取时间戳的单位问题
* **修复** 撤回消息发送器的参数问题

## 4.4.0.56 (2021-04-17)
* **更新** 更新为Mirai 2.6.1
* **更新** 更新为JDK 16
* **更新** 更新部分引用
* **修复** 修复退群后的消息提醒
* **优化**  优化部分代码注释

## 4.3.1.55 (2021-04-06)
* **新增** 添加管理员表
* **新增** 炉石卡牌导入数据库功能
* **优化**  优化功能关闭的提醒
* **优化**  减少MessageChainBuilder调用的次数
* **优化**  更新SQL脚本并添加炉石卡牌和功能触发两张表的导脚本
* **优化**  优化消息判断逻辑代码
* **优化**  优化炉石卡牌文案


## 4.3.0.54 (2021-04-04)
* **更新** Mirai 2.5.1
* **优化** 变量名

## 4.2.1.53 (2021-03-17)
* **优化** 目录结构
* 为 Fuyao Music Room 做准备

## 4.2.0.52 (2021-03-16)
* 去掉MyBatis，改为使用MyBatis Plus (XML爪巴 (复合主键还是XML))
* 重命名数据库
    - bot_game_fishing改为game_fishing
    - hs_card改为game_hs_card
* 修改部分代码，使其更符合代码规范
## 2021-03-14
#### 4.1.8.51
* 规范代码
* 修复问题
## 2021-03-12
#### 4.1.7.50
* 添加点歌功能
* 添加[能不能好好说话](https://github.com/itorr/nbnhhsh)功能
* 修改复读策略：复读一次的消息不会再次复读
* 修复机器人群名片被修改后的监听
* 去除冗余代码和引用
## 2021-03-08
#### 4.1.6.49
* 修复同类型消息任意三条就会触发复读的问题
* 更改部分代码包的位置
* 添加调试功能
* **优化** 炉石开包的图片和文案
* **优化** 其它文案
## 2021-03-06
#### 4.1.5.48
* **优化** 文案
* 添加时间查询、守望先锋当日街机模式查询、炉石抽卡游戏、投骰子和石头剪刀布。
* 更新README
## 2021-03-05
#### 4.1.4.47
* **优化** log文案
* **优化** dev环境判断
* 完善监听的Group事件
* 更新Mirai为2.4.2
## 2021-03-03
#### 4.1.3.46
* 添加发送消息后的log功能
* 添加启动后成功提醒
* 添加复读消息的功能
* 添加红包检测
* **优化** 提醒文案
* **优化** 缓存文件的路径
* **优化** 代码
## 2021-03-01
#### 4.1.2.45
* 修复Linux下的文件路径问题
#### 4.1.1.44
* 更新为 Mirai 2.4.1
* **优化** 入群和群荣誉更改时的文案
* 添加闪照提醒
## 2021-02-25
#### 4.1.0.43
* 更新为 Mirai 2.3.0
* **优化** 入群和群荣誉更改时的文案
* 更新依赖版本
## 2021-01-19
#### 4.0.0.42
* 更新为 Mirai 2.0.0
* 更新依赖版本
* 完全重写，改为使用Mybatis
* **更新** README

</details>

<details>
<summary>3.0.0 +</summary>

## 2020-11-10
#### 3.4.4.41
* 更新为 Mirai 1.3.3
* 更新Spring Boot、hutool等依赖版本
* 去除部分消息提醒
* 暂时关闭点歌功能
## 2020-10-05
#### 3.4.3.40
* 更新为 Mirai 1.3.2
* 去除闪照@提醒
## 2020-09-25
#### 3.4.2.39
* 支持同时@多人戳
* 将功能列表展示方式修改为链接
## 2020-09-19
#### 3.4.1.38
* **优化** 戳一戳
## 2020-09-18
#### 3.4.0.37
* 更新为 Mirai 1.3.0
* 添加戳一戳
## 2020-09-06
#### 3.3.3.36
* 更新早午晚问候文案和时间划分，来自[维基百科](https://zh.wikipedia.org/zh-cn/Template:一天里的时间细分)
* 修复进群欢迎不生效的问题
* 添加新的工具类
* 修改工具类中的函数名
## 2020-08-26
#### 3.3.2.35
* 修复部分文案问题
* 修复算错了的毫秒（1 分钟 60000 毫秒）
* 修改 bot 功能图片获取的方式
* 更新为 Mirai 1.2.2
## 2020-08-22
#### 3.3.1.34 (Hotfix)
* 修复部分功能在私聊中不可用的问题
* 修改部分文案
    * **优化** 入群后第一条消息
* 精简代码
#### 3.3.0.33
* 所有功能可在私聊中使用
* 所有群事件提醒 (如退群、被禁言) 将在1分钟内自动撤回
* 修改部分文案
    * 私聊时的可用功能提醒
    * **优化** 群事件提醒中的群员名片和昵称选择
    * 新成员提醒增加头像
    * 更新日志文案优化
    * 时间文案优化
* 精简代码
## 2020-08-20
#### 3.2.0.32
* 添加自动同意加好友、进群
* 添加处理事件处理时抛出的异常
* 修改协议为Android Pad
#### 3.1.9.31 (Hotfix)
* 修复闪照、红包检测
* 修改部分文案
* 修改协议为Android Watch
#### 3.1.8.30
* 添加群员被kick提醒
* 修改部分文案
* 更新为Mirai 1.2.1
* 更新依赖
## 2020-08-10
#### 3.1.7.29
* 修改部分文案
* 添加API等待提示
## 2020-08-09
#### 3.1.6.28
* 机器人功能列表改为发送图片
* **优化** 部分代码逻辑
* 修改部分文案
* 去除触发日志
## 2020-07-27
#### 3.1.5.27
* 修复机器人被@
* 修改部分文案
## 2020-07-25
#### 3.1.4.26 (Hotfix)
* 修复闪照检测
* 修改签到文案，避免刷屏
#### 3.1.3.25
* 更新为Mirai 1.1.3
* 签到功能测试结束，已全功能上线，测试数据已全部删除
* **优化** 签到功能，修复签到查询和排名
* 实现部分指令功能
## 2020-07-20
#### 3.1.2.24
* **优化** 签到功能，实现签到查询和排名
## 2020-07-17
#### 3.1.1.23
* **优化** 签到功能，实现多群签到
## 2020-07-16
#### 3.1.0.22
* 发送 "sudo get/set" 命令查询和修改参数
* 修复部分问题
* **优化** 文案
## 2020-07-15
#### 3.0.0.21
* 从数据库读取参数和配置
* 修复部分问题

</details>

<details>
<summary>2.0.0 +</summary>

## 2020-07-12
#### 2.3.1.19
* 修复部分问题。
* 添加功能：
    * **优化** 功能17。
* 删除功能
    * 删除功能16、17（部分）
## 2020-07-11
#### 2.3.0.18
* 修复部分问题。
* 添加功能：
    * 实现功能17。
#### 2.2.0.17
* 修复部分问题。
* 添加功能：
    * 实现功能15、16。
## 2020-07-10
#### 2.1.0.16
* 修复部分问题。
* 修改yaml文件匹配关键字的规则。
## 2020-07-09
#### 2.0.0.15
* 重构代码，使用Mirai(https://github.com/mamoe/mirai)
* 添加功能：
    * 实现功能14。

</details>

<details>
<summary>1.0.0 +</summary>

## 2020-07-07
#### 1.1.4.14
* 添加讨论组消息监听和发送。
* 添加功能：
    * 实现功能13。
## 2020-06-29
#### 1.1.3.13
* 修复问题，优化代码。
* 随机复读的阈值改为从配置文件中读取。
## 2020-06-28
#### 1.1.2.12
* 添加网易云点歌。
* 配置文件改为从外部读取。
## 2020-06-27
#### 1.1.1.11
* 添加功能：
    * 实现功能12。
* **优化** 早午晚问候。
#### 1.1.0.10
* 修复复读和闪照。
* 除特别注明外，所有功能群聊和私聊均可用。
## 2020-06-26
#### 1.0.0.9
* 重构代码，使用原生Java，减少内存占用。

</details>

<details>
<summary>0.0.1 +</summary>

## 2020-06-24
#### 0.4.0.8
* 使用反射，重构代码。
* 添加用户和群黑名单功能，在黑名单中的群或用户不会触发概率复读。
* 不会随机复读触发功能的关键字。
#### 0.3.0.7
* 重构代码。
#### 0.2.3.6
* 减少智械危机的发生。
#### 0.2.2.5
* 添加功能：
    * 实现功能10~11。
## 2020-06-23
#### 0.2.1.4
* 添加功能：
    * 实现功能09。
* 重构代码。
#### 0.2.0.3
* 添加功能：
    * 实现功能08。
#### 0.1.0.2
* 添加功能：
    * 实现功能05~07。
* 重构代码。
## 2020-06-22
#### 0.0.1.1
* 首次更新。
    * 实现功能01~04。
</details>
