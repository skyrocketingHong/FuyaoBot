create table bot_admin_user
(
    user_id  bigint                              not null comment '用户QQ号',
    add_user bigint                              not null comment '添加用户',
    add_date timestamp default CURRENT_TIMESTAMP null comment '添加时间，默认为当前时间',
    constraint bot_admin_user_user_id_uindex
        unique (user_id)
)
    comment '管理员用户表';

alter table bot_admin_user
    add constraint `PRIMARY`
        primary key (user_id);

create table bot_baned_group
(
    group_id bigint                              not null
        constraint `PRIMARY`
        primary key,
    add_user bigint                              not null,
    add_date timestamp default CURRENT_TIMESTAMP null
)
    comment '被禁用的群';

create table bot_baned_user
(
    user_id  bigint                              not null
        constraint `PRIMARY`
        primary key,
    add_user bigint                              not null,
    add_date timestamp default CURRENT_TIMESTAMP null
)
    comment '被禁用的用户';

create table bot_config
(
    config_name  varchar(255)                        not null comment 'key'
        constraint `PRIMARY`
        primary key,
    config_value varchar(255)                        not null comment 'value',
    add_date     timestamp default CURRENT_TIMESTAMP null comment '添加事件'
)
    comment '机器人配置表，采用key-value模式。主键为int自增。';

create table bot_function_trigger
(
    id              int auto_increment
        constraint `PRIMARY`
        primary key,
    char_id         varchar(63)          not null comment '字符id',
    trigger_name    varchar(256)         not null comment '功能名',
    trigger_comment varchar(512)         not null comment '功能描述',
    keyword         varchar(256)         not null comment '触发关键字',
    impl_class      varchar(256)         not null comment '触发功能的实现函数',
    enabled         tinyint(1) default 1 null comment '是否启用，默认为true',
    shown           tinyint(1) default 1 null comment '是否在功能列表中展示',
    is_admin        tinyint(1) default 0 null comment '是否需要管理员权限',
    constraint bot_function_trigger_pk
        unique (char_id)
)
    comment '关键词功能触发表';

create table bot_reply_message
(
    reply_key   varchar(256)                        not null
        constraint `PRIMARY`
        primary key,
    reply_value varchar(4096)                       not null,
    add_date    timestamp default CURRENT_TIMESTAMP null
)
    comment '固定回复文案';

create table game_fishing
(
    fish_id          varchar(256)         not null comment '鱼名称id'
        constraint `PRIMARY`
        primary key,
    fish_name        varchar(256)         not null comment '鱼的名称',
    fish_probability double               not null comment '钓到的概率',
    fish_value       bigint               not null comment '鱼的价值',
    is_special       tinyint(1) default 0 not null comment '是否为特殊种类的鱼',
    special_group    bigint               null comment '如果是特殊鱼的话，则需要注明是哪个群的'
)
    comment '钓鱼游戏表';

create table game_hs_card
(
    id     int           not null comment '卡的数字id'
        constraint `PRIMARY`
        primary key,
    `set`  varchar(128)  not null comment '卡所属的扩展包',
    name   varchar(128)  not null comment '卡的名字',
    rarity varchar(64)   not null comment '稀有度',
    imgUrl varchar(1024) null comment '渲染卡牌的图片链接'
)
    comment '炉石卡牌';

create table group_coin
(
    group_id bigint                              not null,
    user_id  bigint                              not null,
    coin     bigint    default 0                 not null,
    get_date timestamp default CURRENT_TIMESTAMP null,
    constraint `PRIMARY`
        primary key (group_id, user_id)
)
    comment '领金币';

create table group_exp
(
    group_id     bigint                              not null,
    user_id      bigint                              not null,
    exp          bigint    default 0                 not null,
    sign_in_date timestamp default CURRENT_TIMESTAMP null,
    constraint `PRIMARY`
        primary key (user_id, group_id)
)
    comment '群聊签到经验值';

create table group_exp_rank_name
(
    group_id   bigint                  not null comment '群号'
        constraint `PRIMARY`
        primary key,
    exp_offset int        default 0    null,
    rank_1     varchar(6) default 'Ⅰ级' null,
    rank_2     varchar(6) default 'Ⅱ级' null,
    rank_3     varchar(6) default 'Ⅲ级' null,
    rank_4     varchar(6) default 'Ⅳ级' null,
    rank_5     varchar(6) default 'Ⅴ级' null,
    rank_6     varchar(6) default 'Ⅵ级' null,
    rank_7     varchar(6) default 'Ⅶ级' null
)
    comment '群内等级名称';

create table group_fishing
(
    group_id bigint       not null comment '群号',
    user_id  bigint       not null comment 'QQ号',
    fish_1   varchar(256) null,
    fish_2   varchar(256) null,
    fish_3   varchar(256) null,
    fish_4   varchar(256) null,
    fish_5   varchar(256) null,
    constraint `PRIMARY`
        primary key (group_id, user_id),
    constraint group_fishing_fk1
        foreign key (fish_1) references game_fishing (fish_id)
            on delete set null,
    constraint group_fishing_fk2
        foreign key (fish_2) references game_fishing (fish_id)
            on delete set null,
    constraint group_fishing_fk3
        foreign key (fish_3) references game_fishing (fish_id)
            on delete set null,
    constraint group_fishing_fk4
        foreign key (fish_4) references game_fishing (fish_id)
            on delete set null,
    constraint group_fishing_fk5
        foreign key (fish_5) references game_fishing (fish_id)
            on delete set null
)
    comment '群内钓鱼信息表';

create table group_function_settings
(
    id             mediumtext   not null comment '群号',
    function_name  varchar(256) not null comment '功能名',
    settings_name  varchar(256) not null comment '设置名称',
    settings_value varchar(256) not null comment '设置对应的值'
);

create table group_timely_message
(
    group_id       bigint               not null,
    user_id        bigint               not null,
    message_string varchar(256)         not null,
    send_time      datetime             not null,
    enabled        tinyint(1) default 1 not null,
    constraint `PRIMARY`
        primary key (group_id, user_id)
);

create table user_coin
(
    user_id  bigint                              not null
        constraint `PRIMARY`
        primary key,
    coin     bigint    default 0                 not null,
    get_date timestamp default CURRENT_TIMESTAMP null
)
    comment '用户领金币';

create table user_exp
(
    user_id      bigint                              not null
        constraint `PRIMARY`
        primary key,
    exp          bigint    default 0                 not null,
    sign_in_date timestamp default CURRENT_TIMESTAMP null
)
    comment '用户签到经验值';

create table user_exp_rank_name
(
    user_id    bigint                  not null comment '群号'
        constraint `PRIMARY`
        primary key,
    exp_offset int        default 0    null,
    rank_1     varchar(6) default 'Ⅰ级' null,
    rank_2     varchar(6) default 'Ⅱ级' null,
    rank_3     varchar(6) default 'Ⅲ级' null,
    rank_4     varchar(6) default 'Ⅳ级' null,
    rank_5     varchar(6) default 'Ⅴ级' null,
    rank_6     varchar(6) default 'Ⅵ级' null,
    rank_7     varchar(6) default 'Ⅶ级' null
)
    comment '私聊签到等级名称';


