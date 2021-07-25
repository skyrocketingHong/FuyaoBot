create table if not exists group_timely_message
(
	group_id bigint not null,
	user_id bigint not null,
	message_string varchar(256) not null,
	send_time datetime not null,
	enabled tinyint(1) default 1 not null,
	constraint `PRIMARY`
		primary key (group_id, user_id)
);

