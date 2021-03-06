create table bot_config
(
	config_name varchar(255) not null comment 'key'
		constraint `PRIMARY`
			primary key,
	config_value varchar(255) not null comment 'value',
	add_date timestamp default CURRENT_TIMESTAMP null comment '添加事件'
)
comment '机器人配置表，采用key-value模式。主键为int自增。';

