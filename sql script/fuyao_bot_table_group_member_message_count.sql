
CREATE TABLE IF NOT EXISTS `group_member_message_count` (
  `group_id` bigint NOT NULL COMMENT '群号',
  `user_id` bigint NOT NULL COMMENT '群员QQ号',
  `last_update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `message_count` int NOT NULL DEFAULT '0' COMMENT '消息数量',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群员消息数量统计';
