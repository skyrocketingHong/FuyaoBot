create table if not exists group_rss_message
(
	id int auto_increment
		constraint `PRIMARY`
			primary key,
	group_id bigint not null comment '群号',
	user_id bigint not null comment '用户QQ号',
	rss_url varchar(2083) not null comment 'RSS链接',
	add_date timestamp default CURRENT_TIMESTAMP null comment '添加时间',
	enabled tinyint(1) default 1 not null comment '是否启用',
	last_notified_date timestamp null comment '最后提醒时间',
	last_notified_url varchar(2083) null comment '最后提醒的链接'
)
comment 'RSS提醒表';

