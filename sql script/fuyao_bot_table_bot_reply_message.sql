
CREATE TABLE IF NOT EXISTS `bot_reply_message` (
  `reply_key` varchar(256) NOT NULL,
  `reply_value` varchar(4096) NOT NULL,
  `add_date` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`reply_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='固定回复文案';
