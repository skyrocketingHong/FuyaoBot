create table game_fishing
(
	fish_id varchar(256) not null comment '鱼名称id'
		constraint `PRIMARY`
			primary key,
	fish_name varchar(256) not null comment '鱼的名称',
	fish_probability double not null comment '钓到的概率',
	fish_value bigint not null comment '鱼的价值',
	is_special tinyint(1) default 0 not null comment '是否为特殊种类的鱼',
	special_group bigint null comment '如果是特殊鱼的话，则需要注明是哪个群的'
)
comment '钓鱼游戏表';
