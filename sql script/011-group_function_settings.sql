create table if not exists group_function_settings
(
	id mediumtext not null comment '群号',
	function_name varchar(256) not null comment '功能名',
	settings_name varchar(256) not null comment '设置名称',
	settings_value varchar(256) not null comment '设置对应的值'
);

