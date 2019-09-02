create database sso_service;

CREATE TABLE `sso_service`.`userTable` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `user_role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`));

insert into sso_service.userTable(`user_name`,`user_password`,`user_role`) values("hezhiqiang", "123456","NORMAL");

SELECT * FROM sso_service.userTable;

create table user_map_faceid
(
	id int auto_increment,
	user_name varchar(20) not null,
	face_id varchar(20) not null,
	constraint user_map_faceid_pk
		primary key (id)
);

INSERT INTO `sso_service`.`user_map_faceid` (`user_name`, `face_id`) VALUES ('hezhiqiang', 'jessehe');

