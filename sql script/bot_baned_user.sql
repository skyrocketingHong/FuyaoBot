create table bot_baned_user
(
	user_id bigint not null
		constraint `PRIMARY`
			primary key,
	add_user bigint not null,
	add_date timestamp default CURRENT_TIMESTAMP null
)
comment '被禁用的用户';

