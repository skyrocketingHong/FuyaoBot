create table hs_card
(
	id varchar(32) not null comment '卡的数字id'
		constraint `PRIMARY`
			primary key,
	`set` varchar(128) not null comment '卡所属的扩展包',
	name varchar(128) not null comment '卡的名字',
	rarity varchar(16) not null comment '稀有度',
	imgUrl varchar(1024) null comment '渲染卡牌的图片链接'
)
comment '炉石卡牌';

