CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'dev'@'localhost';


# Create DB
CREATE DATABASE IF NOT EXISTS `users_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `users_db`;


# Create Table
CREATE TABLE IF NOT EXISTS `users_db`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10000073
DEFAULT CHARACTER SET = utf8;