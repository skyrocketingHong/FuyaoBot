
CREATE TABLE IF NOT EXISTS `user_coin` (
  `user_id` bigint NOT NULL,
  `coin` bigint NOT NULL DEFAULT '0',
  `get_date` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户领金币';
