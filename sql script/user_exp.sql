create table user_exp
(
	user_id bigint not null
		constraint `PRIMARY`
			primary key,
	exp bigint default 0 not null,
	sign_in_date timestamp default CURRENT_TIMESTAMP null
)
comment '用户签到经验值';

