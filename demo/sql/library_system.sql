/*
 Navicat Premium Dump SQL

 Source Server         : mysql_locallhost
 Source Server Type    : MySQL
 Source Server Version : 90001 (9.0.1)
 Source Host           : localhost:3306
 Source Schema         : library_system

 Target Server Type    : MySQL
 Target Server Version : 90001 (9.0.1)
 File Encoding         : 65001

 Date: 05/06/2025 15:34:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
DROP TABLE IF EXISTS `users`;
create table users
(
    userid      varchar(14)                                      not null
        primary key,
    username    varchar(50)                                      not null,
    password    varchar(100)                                     not null,
    email       varchar(100)                                     null,
    phoneNumber varchar(11)                                      null,
    role        enum ('admin', 'user') default 'user'            null,
    created_at  datetime               default CURRENT_TIMESTAMP null,
    updated_at  datetime               default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

-- ----------------------------------------------
DROP TABLE IF EXISTS `books`;
create table books
(
    book_id        varchar(14)   not null
        primary key,
    title          varchar(255)  not null,
    author         varchar(255)  not null,
    publisher      varchar(255)  null,
    published_date date          null,
    isbn           varchar(13)   null,
    category       varchar(100)  null,
    stock_quantity int default 0 null,
    constraint isbn
        unique (isbn)
);

-- ----------------------------------------------
DROP TABLE IF EXISTS `bookDetails`;
create table bookDetails
(
    detail_id  varchar(18)                                                      not null
        primary key,
    book_id    varchar(14)                                                      null,
    status     enum ('available', 'borrowed', 'lost') default 'available'       null,
    added_date timestamp                              default CURRENT_TIMESTAMP null,
    constraint bookDetails_ibfk_1
        foreign key (book_id) references books (book_id)
            on delete cascade
);

create index book_id
    on bookDetails (book_id);
-- ----------------------------------------------
DROP TABLE IF EXISTS `borrowHistory`;
create table borrowHistory
(
    history_id bigint auto_increment
        primary key,
    userid     varchar(100)                       not null,
    detail_id  varchar(100)                       not null,
    borrow_at  datetime                           not null,
    return_at  datetime                           null,
    create_at  datetime default CURRENT_TIMESTAMP null,
    update_at  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    charset = utf8;



SET FOREIGN_KEY_CHECKS = 1;
