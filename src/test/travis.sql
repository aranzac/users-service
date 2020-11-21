CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'dev'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'root'@'localhost';


# Create DB
CREATE DATABASE IF NOT EXISTS `users_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `users_db`;


# Create Table
CREATE TABLE IF NOT EXISTS `users_db`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10000077
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `users_db`.`roles` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO roles(id, name) VALUES(0, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES(1, 'ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS `users_db`.`users_has_roles` (
  `users_id` INT(11) NOT NULL,
  `roles_id` INT(11) NOT NULL,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `users_db`.`roles` (`id`),
  CONSTRAINT `fk_users_has_roles_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `users_db`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

