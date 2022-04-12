
-- --------------------------------------------------------

--
-- 表的结构 `game_hs_card`
--

CREATE TABLE IF NOT EXISTS `game_hs_card` (
  `id` int NOT NULL COMMENT '卡的数字id',
  `set` varchar(128) NOT NULL COMMENT '卡所属的扩展包',
  `name` varchar(128) NOT NULL COMMENT '卡的名字',
  `rarity` varchar(64) NOT NULL COMMENT '稀有度',
  `imgUrl` varchar(1024) DEFAULT NULL COMMENT '渲染卡牌的图片链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='炉石卡牌';
