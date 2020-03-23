create database shell_db;
use shell_db;
CREATE TABLE `em_admin`(
    id int(10) not null AUTO_INCREMENT,
    lastname varchar(20),
    firstname varchar(20),
    primary key(`id`)
) engine=InnoDB AUTO_INCREMENT=1 default character set =utf8mb4;