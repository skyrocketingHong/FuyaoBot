create table group_exp
(
	group_id bigint not null,
	user_id bigint not null,
	exp bigint default 0 not null,
	sign_in_date timestamp default CURRENT_TIMESTAMP null,
	constraint `PRIMARY`
		primary key (user_id, group_id)
)
comment '群聊签到经验值';

