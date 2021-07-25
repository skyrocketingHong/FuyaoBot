create table if not exists bot_admin_user
(
	user_id bigint not null comment '用户QQ号',
	add_user bigint not null comment '添加用户',
	add_date timestamp default CURRENT_TIMESTAMP null comment '添加时间，默认为当前时间',
	constraint bot_admin_user_user_id_uindex
		unique (user_id)
)
comment '管理员用户表';

alter table bot_admin_user
	add constraint `PRIMARY`
		primary key (user_id);

