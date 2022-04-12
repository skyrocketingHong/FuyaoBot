
-- --------------------------------------------------------

--
-- 表的结构 `bot_baned_user`
--

CREATE TABLE IF NOT EXISTS `bot_baned_user` (
  `user_id` bigint NOT NULL,
  `add_user` bigint NOT NULL,
  `add_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='被禁用的用户';
