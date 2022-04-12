
-- --------------------------------------------------------

--
-- 表的结构 `bot_admin_user`
--

CREATE TABLE IF NOT EXISTS `bot_admin_user` (
  `user_id` bigint NOT NULL COMMENT '用户QQ号',
  `add_user` bigint NOT NULL COMMENT '添加用户',
  `add_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间，默认为当前时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `bot_admin_user_user_id_uindex` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员用户表';
