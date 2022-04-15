
CREATE TABLE IF NOT EXISTS `group_function_settings` (
  `id` mediumtext NOT NULL COMMENT '群号',
  `function_name` varchar(256) NOT NULL COMMENT '功能名',
  `settings_name` varchar(256) NOT NULL COMMENT '设置名称',
  `settings_value` varchar(256) NOT NULL COMMENT '设置对应的值',
  `modified_date` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
