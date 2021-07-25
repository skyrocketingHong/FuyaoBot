create table if not exists game_hs_card
(
	id int not null comment '卡的数字id'
		constraint `PRIMARY`
			primary key,
	`set` varchar(128) not null comment '卡所属的扩展包',
	name varchar(128) not null comment '卡的名字',
	rarity varchar(64) not null comment '稀有度',
	imgUrl varchar(1024) null comment '渲染卡牌的图片链接'
)
comment '炉石卡牌';

