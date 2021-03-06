create table bot_function_trigger
(
	id int auto_increment
		constraint `PRIMARY`
			primary key,
	char_id varchar(63) not null comment '字符id',
	trigger_name varchar(256) not null comment '功能名',
	trigger_comment varchar(512) not null comment '功能描述',
	keyword varchar(256) not null comment '触发关键字',
	impl_class varchar(256) not null comment '触发功能的实现函数',
	enabled tinyint(1) default 1 null comment '是否启用，默认为true',
	shown tinyint(1) default 1 null comment '是否在功能列表中展示',
	constraint bot_function_trigger_pk
		unique (char_id)
)
comment '关键词功能触发表';

