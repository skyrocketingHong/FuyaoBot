
-- --------------------------------------------------------

--
-- 表的结构 `group_fishing`
--

CREATE TABLE IF NOT EXISTS `group_fishing` (
  `group_id` bigint NOT NULL COMMENT '群号',
  `user_id` bigint NOT NULL COMMENT 'QQ号',
  `fish_1` varchar(256) DEFAULT NULL,
  `fish_2` varchar(256) DEFAULT NULL,
  `fish_3` varchar(256) DEFAULT NULL,
  `fish_4` varchar(256) DEFAULT NULL,
  `fish_5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `group_fishing_fk1` (`fish_1`),
  KEY `group_fishing_fk2` (`fish_2`),
  KEY `group_fishing_fk3` (`fish_3`),
  KEY `group_fishing_fk4` (`fish_4`),
  KEY `group_fishing_fk5` (`fish_5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群内钓鱼信息表';
