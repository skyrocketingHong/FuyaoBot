
CREATE TABLE IF NOT EXISTS `group_rss_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL COMMENT '群号',
  `user_id` bigint NOT NULL COMMENT '用户QQ号',
  `rss_url` varchar(2083) NOT NULL COMMENT 'RSS链接',
  `add_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '添加时间',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `notify_keywords` varchar(2083) DEFAULT NULL COMMENT '提醒关键词的正则表达式',
  `last_notified_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后提醒时间',
  `last_notified_url` varchar(2083) DEFAULT NULL COMMENT '最后提醒的链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RSS提醒表';
