
CREATE TABLE IF NOT EXISTS `bot_config` (
  `config_name` varchar(255) NOT NULL COMMENT 'key',
  `config_value` varchar(255) NOT NULL COMMENT 'value',
  `add_date` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '添加事件',
  PRIMARY KEY (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='机器人配置表，采用key-value模式。主键为int自增。';
