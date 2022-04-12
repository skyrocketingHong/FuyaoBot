
-- --------------------------------------------------------

--
-- 表的结构 `group_exp_rank_name`
--

CREATE TABLE IF NOT EXISTS `group_exp_rank_name` (
  `group_id` bigint NOT NULL COMMENT '群号',
  `exp_offset` int DEFAULT '0',
  `rank_1` varchar(6) DEFAULT 'Ⅰ级',
  `rank_2` varchar(6) DEFAULT 'Ⅱ级',
  `rank_3` varchar(6) DEFAULT 'Ⅲ级',
  `rank_4` varchar(6) DEFAULT 'Ⅳ级',
  `rank_5` varchar(6) DEFAULT 'Ⅴ级',
  `rank_6` varchar(6) DEFAULT 'Ⅵ级',
  `rank_7` varchar(6) DEFAULT 'Ⅶ级',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群内等级名称';
