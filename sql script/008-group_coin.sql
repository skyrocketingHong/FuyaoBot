create table if not exists group_coin
(
	group_id bigint not null,
	user_id bigint not null,
	coin bigint default 0 not null,
	get_date timestamp default CURRENT_TIMESTAMP null,
	constraint `PRIMARY`
		primary key (group_id, user_id)
)
comment '领金币';

