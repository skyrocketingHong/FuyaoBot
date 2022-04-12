
-- --------------------------------------------------------

--
-- 表的结构 `group_timely_message`
--

CREATE TABLE IF NOT EXISTS `group_timely_message` (
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `message_string` varchar(256) NOT NULL,
  `send_time` datetime NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
