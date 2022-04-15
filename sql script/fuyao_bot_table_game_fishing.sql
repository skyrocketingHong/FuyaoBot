
CREATE TABLE IF NOT EXISTS `game_fishing` (
  `fish_id` varchar(256) NOT NULL COMMENT '鱼名称id',
  `fish_name` varchar(256) NOT NULL COMMENT '鱼的名称',
  `fish_probability` double NOT NULL COMMENT '钓到的概率',
  `fish_value` bigint NOT NULL COMMENT '鱼的价值',
  `is_special` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为特殊种类的鱼',
  `special_group` bigint DEFAULT NULL COMMENT '如果是特殊鱼的话，则需要注明是哪个群的',
  PRIMARY KEY (`fish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钓鱼游戏表';
