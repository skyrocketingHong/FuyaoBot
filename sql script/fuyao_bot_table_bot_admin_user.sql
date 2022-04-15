
CREATE TABLE IF NOT EXISTS `bot_admin_user` (
  `user_id` bigint NOT NULL COMMENT '用户QQ号',
  `add_user` bigint NOT NULL COMMENT '添加用户',
  `add_date` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '添加时间，默认为当前时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `bot_admin_user_user_id_uindex` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员用户表';
