create table bot_reply_message
(
	reply_key varchar(256) not null
		constraint `PRIMARY`
			primary key,
	reply_value varchar(4096) not null,
	add_date timestamp default CURRENT_TIMESTAMP null
)
comment '固定回复文案';

