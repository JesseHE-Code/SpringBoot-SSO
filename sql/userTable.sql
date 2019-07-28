create database sso_service;

CREATE TABLE `sso_service`.`userTable` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `user_role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`));

SELECT * FROM sso_service.userTable;
