
-- --------------------------------------------------------

--
-- 表的结构 `group_exp`
--

CREATE TABLE IF NOT EXISTS `group_exp` (
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `exp` bigint NOT NULL DEFAULT '0',
  `sign_in_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群聊签到经验值';
