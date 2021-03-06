create table group_fishing
(
	group_id bigint not null comment '群号',
	user_id bigint not null comment 'QQ号',
	fish_1 varchar(256) null,
	fish_2 varchar(256) null,
	fish_3 varchar(256) null,
	fish_4 varchar(256) null,
	fish_5 varchar(256) null,
	constraint `PRIMARY`
		primary key (group_id, user_id),
	constraint group_fishing_fk1
		foreign key (fish_1) references bot_game_fishing (fish_id)
			on delete set null,
	constraint group_fishing_fk2
		foreign key (fish_2) references bot_game_fishing (fish_id)
			on delete set null,
	constraint group_fishing_fk3
		foreign key (fish_3) references bot_game_fishing (fish_id)
			on delete set null,
	constraint group_fishing_fk4
		foreign key (fish_4) references bot_game_fishing (fish_id)
			on delete set null,
	constraint group_fishing_fk5
		foreign key (fish_5) references bot_game_fishing (fish_id)
			on delete set null
)
comment '群内钓鱼信息表';

