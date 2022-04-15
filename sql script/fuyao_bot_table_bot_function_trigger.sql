
CREATE TABLE IF NOT EXISTS `bot_function_trigger` (
  `id` int NOT NULL AUTO_INCREMENT,
  `char_id` varchar(63) NOT NULL COMMENT '字符id',
  `trigger_name` varchar(256) NOT NULL COMMENT '功能名',
  `trigger_comment` varchar(512) NOT NULL COMMENT '功能描述',
  `keyword` varchar(256) NOT NULL COMMENT '触发关键字',
  `impl_class` varchar(256) NOT NULL COMMENT '触发功能的实现函数',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用，默认为true',
  `shown` tinyint(1) DEFAULT '1' COMMENT '是否在功能列表中展示',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否需要管理员权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bot_function_trigger_pk` (`char_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关键词功能触发表';

INSERT INTO `bot_function_trigger` (`id`, `char_id`, `trigger_name`, `trigger_comment`, `keyword`, `impl_class`, `enabled`, `shown`, `is_admin`) VALUES
(1, 'sign-in', '签到', '发送签到，获取经验值', '^签到$', 'function.ExpFunction.signIn', 1, 1, 0),
(2, 'sign-in-query', '签到经验值查询', '发送EXP查询，获取本人在该群的经验值', '^(EXP|exp|经验)查询$', 'function.ExpFunction.expQuery', 1, 1, 0),
(3, 'get-coin', '领金币', '发送领金币，获取金币', '^领金币$', 'function.CoinFunction.getCoin', 1, 1, 0),
(4, 'get-coin-query', '金币查询', '发送金币查询，获取本群的经验值', '^金币查询$', 'function.CoinFunction.coinQuery', 1, 1, 0),
(5, 'coin-transform', '金币转移', '发送“金币转移 数量 @接收人”，可以将金币转移给他人', '^金币转移.+', 'function.CoinFunction.coinTransform', 1, 1, 0),
(6, 'fishing', '钓鱼', '发送“钓鱼”，就可以钓一条鱼', '^钓鱼$', 'function.FishingFunction.fishByExpAndCoin', 1, 1, 0),
(7, 'fish-type-query', '鱼种查询', '发送“鱼种查询”', '^鱼种查询$', 'function.FishingFunction.fishTypeQuery', 1, 1, 0),
(8, 'fish-tub-query', '鱼筐查询/鱼框状态', '鱼筐查询/鱼框状态', '^鱼筐(查询|状态)$', 'function.FishingFunction.fishTubQuery', 1, 1, 0),
(9, 'get-bonus-coin', '福利金币', '领取福利金币', '^福利金币$', 'function.CoinFunction.bonusCoin', 1, 0, 0),
(10, 'fish-sell', '卖鱼', '卖鱼', '^卖鱼.*', 'function.FishingFunction.sellFish', 1, 1, 0),
(11, 'ow-mode', '守望先锋街机模式查询', '守望先锋街机模式查询', '^守望街机模式|ow[\\s|-]?mode$', 'function.QueryFunction.getOverwatchArcadeModes', 1, 1, 0),
(12, 'roll-a-dice', '投骰子', '投骰子', '^投骰子|roll[\\s|-]?dice$', 'function.SmallGamesFunction.rollADice', 1, 1, 0),
(13, 'rock-paper-scissors', '石头剪刀布', '石头剪刀布', '^(剪刀|石头|布){3}|(r|p|s){3}$', 'function.SmallGamesFunction.rockPaperScissors', 1, 1, 0),
(14, 'time', '当前时间', '获取当前时间', '^时间|time$', 'function.QueryFunction.time', 1, 1, 0),
(15, 'hs-card', '炉石传说开包', '炉石传说开包', '^炉石传说|hs$', 'function.SmallGamesFunction.hearthStone', 1, 1, 0),
(16, 'nbnhhsh', '能不能好好说话', '能不能好好说话，将缩写转换为全称', '^wtf\\s+\\S+', 'function.QueryFunction.nbnhhsh', 1, 1, 0),
(17, 'music', '点歌', '点歌', '^music\\s+.*', 'function.QueryFunction.music', 1, 1, 0),
(18, 'debug', 'debug测试', 'debug测试', '^debug$', 'function.DebugFunction.debug', 1, 0, 1),
(19, 'hs-debug', '炉石卡牌debug', '炉石卡牌debug', '^hs[\\s|-]?debug\\s([A-Z]+_?)+[A-Z]+\\s+.*', 'function.DebugFunction.hearthStoneDebug', 1, 0, 1),
(20, 'group-settings', '群内设置查询', '群内设置查询', '^group[\\s|-]?settings$', 'function.QueryFunction.groupSettings', 1, 1, 0),
(21, 'bot-status', 'bot状态查询', 'bot状态查询', '^bot[\\s|-]?status$', 'function.QueryFunction.botStatus', 1, 1, 0),
(22, 'feedback-message', '扶摇 bot 反馈', '收到反馈后会发送给开发者', '^feedback\\s+\\S+', 'function.ConfigFunction.feedbackMessage', 1, 1, 0),
(23, 'message-count', '消息数量查询', '消息数量查询', '^message[\\s|-]count$', 'function.QueryFunction.messageCount', 1, 1, 0);
