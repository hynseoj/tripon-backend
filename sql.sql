-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tripon
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tripon` ;

-- -----------------------------------------------------
-- Schema tripon
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tripon` DEFAULT CHARACTER SET utf8mb4 ;
USE `tripon` ;

-- -----------------------------------------------------
-- Table `tripon`.`contenttypes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`contenttypes` ;

CREATE TABLE IF NOT EXISTS `tripon`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`sidos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`sidos` ;

CREATE TABLE IF NOT EXISTS `tripon`.`sidos` (
  `no` INT NOT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `sido_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`guguns`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`guguns` ;

CREATE TABLE IF NOT EXISTS `tripon`.`guguns` (
  `no` INT NOT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `gugun_code` (`gugun_code` ASC) VISIBLE,
  INDEX `sido_code` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `guguns_ibfk_1`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripon`.`sidos` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`attractions` ;

CREATE TABLE IF NOT EXISTS `tripon`.`attractions` (
  `no` INT NOT NULL,
  `content_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `content_type_id` (`content_type_id` ASC) VISIBLE,
  INDEX `area_code` (`area_code` ASC) VISIBLE,
  INDEX `sigungu_code` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_ibfk_1`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `tripon`.`contenttypes` (`content_type_id`),
  CONSTRAINT `attractions_ibfk_2`
    FOREIGN KEY (`area_code`)
    REFERENCES `tripon`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_ibfk_3`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `tripon`.`guguns` (`gugun_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`members` ;

CREATE TABLE IF NOT EXISTS `tripon`.`members` (
  `email` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `role` ENUM('ADMIN', 'USER') NOT NULL,
  `profile_picture_original` VARCHAR(500) NULL DEFAULT NULL,
  `profile_picture_modify` VARCHAR(500) NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`reviews` ;

CREATE TABLE IF NOT EXISTS `tripon`.`reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_email` VARCHAR(50) NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `member_email` (`member_email` ASC) VISIBLE,
  CONSTRAINT `reviews_ibfk_1`
    FOREIGN KEY (`member_email`)
    REFERENCES `tripon`.`members` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`comments` ;

CREATE TABLE IF NOT EXISTS `tripon`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `review_id` INT NOT NULL,
  `parent_id` INT NOT NULL,
  `member_id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `review_id` (`review_id` ASC) VISIBLE,
  CONSTRAINT `comments_ibfk_1`
    FOREIGN KEY (`review_id`)
    REFERENCES `tripon`.`reviews` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`custom_attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`custom_attractions` ;

CREATE TABLE IF NOT EXISTS `tripon`.`custom_attractions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `area_code` (`area_code` ASC) VISIBLE,
  INDEX `sigungu_code` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `custom_attractions_ibfk_1`
    FOREIGN KEY (`area_code`)
    REFERENCES `tripon`.`sidos` (`sido_code`),
  CONSTRAINT `custom_attractions_ibfk_2`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `tripon`.`guguns` (`gugun_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 500000
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`liked_reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`liked_reviews` ;

CREATE TABLE IF NOT EXISTS `tripon`.`liked_reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `review_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `email` (`email` ASC) VISIBLE,
  INDEX `review_id` (`review_id` ASC) VISIBLE,
  CONSTRAINT `liked_reviews_ibfk_1`
    FOREIGN KEY (`email`)
    REFERENCES `tripon`.`members` (`email`),
  CONSTRAINT `liked_reviews_ibfk_2`
    FOREIGN KEY (`review_id`)
    REFERENCES `tripon`.`reviews` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`notices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`notices` ;

CREATE TABLE IF NOT EXISTS `tripon`.`notices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `email` (`email` ASC) VISIBLE,
  CONSTRAINT `notices_ibfk_1`
    FOREIGN KEY (`email`)
    REFERENCES `tripon`.`members` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`reviewdetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`reviewdetails` ;

CREATE TABLE IF NOT EXISTS `tripon`.`reviewdetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `review_id` INT NOT NULL,
  `day` INT NOT NULL,
  `content` VARCHAR(10000) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `review_id` (`review_id` ASC) VISIBLE,
  CONSTRAINT `reviewdetails_ibfk_1`
    FOREIGN KEY (`review_id`)
    REFERENCES `tripon`.`reviews` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`pictures`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`pictures` ;

CREATE TABLE IF NOT EXISTS `tripon`.`pictures` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reviewdetail_id` INT NOT NULL,
  `picture_original` VARCHAR(500) NOT NULL,
  `picture_modify` VARCHAR(500) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `reviewdetail_id` (`reviewdetail_id` ASC) VISIBLE,
  CONSTRAINT `pictures_ibfk_1`
    FOREIGN KEY (`reviewdetail_id`)
    REFERENCES `tripon`.`reviewdetails` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`plans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`plans` ;

CREATE TABLE IF NOT EXISTS `tripon`.`plans` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `memo` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `email` (`email` ASC) VISIBLE,
  CONSTRAINT `plans_ibfk_1`
    FOREIGN KEY (`email`)
    REFERENCES `tripon`.`members` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`plandetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`plandetails` ;

CREATE TABLE IF NOT EXISTS `tripon`.`plandetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `day` INT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `plan_id` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `plandetails_ibfk_1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `tripon`.`plans` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`plan_attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`plan_attractions` ;

CREATE TABLE IF NOT EXISTS `tripon`.`plan_attractions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plandetail_id` INT NOT NULL,
  `attraction_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `plan_attractions_ibfk_1_idx` (`plandetail_id` ASC) VISIBLE,
  CONSTRAINT `plan_attractions_ibfk_1`
    FOREIGN KEY (`plandetail_id`)
    REFERENCES `tripon`.`plandetails` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`review_attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`review_attractions` ;

CREATE TABLE IF NOT EXISTS `tripon`.`review_attractions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reviewdetail_id` INT NOT NULL,
  `attraction_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `reviewdetail_id` (`reviewdetail_id` ASC) VISIBLE,
  INDEX `attraction_no` (`attraction_id` ASC) VISIBLE,
  CONSTRAINT `review_attractions_ibfk_1`
    FOREIGN KEY (`reviewdetail_id`)
    REFERENCES `tripon`.`reviewdetails` (`id`),
  CONSTRAINT `review_attractions_ibfk_2`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `tripon`.`attractions` (`no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`tags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`tags` ;

CREATE TABLE IF NOT EXISTS `tripon`.`tags` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `review_id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `review_id` (`review_id` ASC) VISIBLE,
  CONSTRAINT `tags_ibfk_1`
    FOREIGN KEY (`review_id`)
    REFERENCES `tripon`.`reviews` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into members (email, name, password, role) values ('admin@ssafy.com', 'admin', '1234', 'ADMIN');
insert into plans (email, title, start_date, end_date, memo) 
values ('admin@ssafy.com', 'admin', '2025-04-01', '2025-04-04', '첫번째 계획!');

select * from plans;
