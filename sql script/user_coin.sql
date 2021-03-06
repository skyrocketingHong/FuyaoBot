create table user_coin
(
	user_id bigint not null
		constraint `PRIMARY`
			primary key,
	coin bigint default 0 not null,
	get_date timestamp default CURRENT_TIMESTAMP null
)
comment '用户领金币';

