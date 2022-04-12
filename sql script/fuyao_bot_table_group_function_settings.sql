
-- --------------------------------------------------------

--
-- 表的结构 `group_function_settings`
--

CREATE TABLE IF NOT EXISTS `group_function_settings` (
  `group_id` bigint NOT NULL COMMENT '群号',
  `function_id` varchar(128) NOT NULL COMMENT '功能名',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `parameter` varchar(1024) DEFAULT NULL COMMENT '可选参数',
  `user_id` bigint NOT NULL COMMENT '修改人',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE KEY `group_function_settings_pk` (`group_id`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
