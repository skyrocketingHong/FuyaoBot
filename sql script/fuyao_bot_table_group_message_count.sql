
CREATE TABLE IF NOT EXISTS `group_message_count` (
  `group_id` bigint NOT NULL,
  `message_count` int NOT NULL DEFAULT '0',
  `last_update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `yesterday_message_count` int DEFAULT '0',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_id_group_id_uindex` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群消息数量统计';
