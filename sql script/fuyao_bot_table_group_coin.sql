
-- --------------------------------------------------------

--
-- 表的结构 `group_coin`
--

CREATE TABLE IF NOT EXISTS `group_coin` (
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `coin` bigint NOT NULL DEFAULT '0',
  `get_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='领金币';
