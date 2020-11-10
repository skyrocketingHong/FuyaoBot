# 1. QQRobot
A QQ robot based on [Mirai](https://github.com/mamoe/mirai).
# 2. 已实现功能
00. 可能有小彩蛋。
01. 点歌（默认使用QQ音乐），网易云点歌（1.1.2中添加）。
02. 考研倒计时。
03. 简易人工智能（群聊，随机回复，10%）。
04. 当前时间（北京时间和太平洋标准时）。
05. 早午晚问候（1.1.1版已优化）。
06. 随机复读群聊文本消息（群聊，2%）。
07. 查看所有功能。
08. 投骰子和石头剪刀布。
09. 查询群友最后发言时间（群聊）。
10. 啊这，就这？（群聊，随机复读，10%）。
11. 100%复读指令。
12. 输出日历。
13. 生成一定数量的随机数。
14. 点个视频。
15. 机器人触发后的消息提醒。
16. ~~整点报时（部分群）。~~
17. 群员进群/退群/踢出/被禁言/解除禁言/设为管理员/取消都会提醒，并将在 1 分钟内撤回
18. 守望先锋街机模式列表。
19. 发送 "sudo get/set" 命令查询和修改参数。
20. 多群签到。

# 3. 数据库脚本

```
咕咕咕
```

# 4. 更新日志

## 2020-11-10

#### 3.4.4.41

* 更新为 Mirai 1.3.3
* 更新Springboot、hutool等依赖版本
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

* 优化戳一戳

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
    * 优化入群后第一条消息
* 精简代码
#### 3.3.0.33
* 所有功能可在私聊中使用
* 所有群事件提醒 (如退群、被禁言) 将在1分钟内自动撤回
* 修改部分文案
    * 私聊时的可用功能提醒
    * 优化群事件提醒中的群员名片和昵称选择
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
* 优化部分代码逻辑
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
* 优化签到功能，修复签到查询和排名
* 实现部分指令功能
## 2020-07-20
#### 3.1.2.24
* 优化签到功能，实现签到查询和排名
## 2020-07-17
#### 3.1.1.23
* 优化签到功能，实现多群签到
## 2020-07-16
#### 3.1.0.22
* 发送 "sudo get/set" 命令查询和修改参数
* 修复部分问题
* 优化文案
## 2020-07-15
#### 3.0.0.21
* 从数据库读取参数和配置
* 修复部分问题
## 2020-07-12
#### 2.3.1.19
* 修复部分问题。
* 添加功能：
    * 优化功能17。
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
* 优化早午晚问候。
#### 1.1.0.10
* 修复复读和闪照。
* 除特别注明外，所有功能群聊和私聊均可用。
## 2020-06-26
#### 1.0.0.9
* 重构代码，使用原生Java，减少内存占用。
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