create table user_exp_rank_name
(
	user_id bigint not null comment '群号'
		constraint `PRIMARY`
			primary key,
	exp_offset int default 0 null,
	rank_1 varchar(6) default 'Ⅰ级' null,
	rank_2 varchar(6) default 'Ⅱ级' null,
	rank_3 varchar(6) default 'Ⅲ级' null,
	rank_4 varchar(6) default 'Ⅳ级' null,
	rank_5 varchar(6) default 'Ⅴ级' null,
	rank_6 varchar(6) default 'Ⅵ级' null,
	rank_7 varchar(6) default 'Ⅶ级' null
)
comment '私聊签到等级名称';

