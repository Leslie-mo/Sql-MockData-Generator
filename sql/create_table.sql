-- create database
create database if not exists sqlgenerator;

-- use database
use sqlgenerator;

-- table info
create table if not exists table_info
(
    id            bigint auto_increment comment 'id' primary key,
    name          varchar(512)                       null comment 'name',
    content       text                               null comment 'table content（json）',
    reviewStatus  int      default 0                 not null comment 'review status（0-pending, 1-passed, 2-rejected）',
    reviewMessage varchar(512)                       null comment 'review message',
    userId        bigint                             not null comment 'Create user id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete      tinyint  default 0                 not null comment 'whether is delete'
) comment 'table info';
