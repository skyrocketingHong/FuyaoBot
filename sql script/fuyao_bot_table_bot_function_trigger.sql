
-- --------------------------------------------------------

--
-- 表的结构 `bot_function_trigger`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关键词功能触发表';
