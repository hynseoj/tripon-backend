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
  `content_type_id` int NOT NULL comment '콘텐츠타입번호',
  `content_type_name` varchar(45) DEFAULT NULL comment '콘텐츠타입이름',
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci comment '콘텐츠타입정보테이블';


-- -----------------------------------------------------
-- Table `tripon`.`sidos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`sidos` ;

CREATE TABLE IF NOT EXISTS `tripon`.`sidos` (
  `no` int NOT NULL AUTO_INCREMENT  comment '시도번호',
  `sido_code` int NOT NULL comment '시도코드',
  `sido_name` varchar(20) DEFAULT NULL comment '시도이름',
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci comment '시도정보테이블';


-- -----------------------------------------------------
-- Table `tripon`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tripon`.`guguns` (
  `no` int NOT NULL AUTO_INCREMENT comment '구군번호',
  `sido_code` int NOT NULL comment '시도코드',
  `gugun_code` int NOT NULL comment '구군코드',
  `gugun_name` varchar(20) DEFAULT NULL comment '구군이름',
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripon`.`sidos` (`sido_code`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '구군정보테이블';


-- -----------------------------------------------------
-- Table `tripon`.`attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`attractions` ;

CREATE TABLE `attractions` (
  `no` int NOT NULL AUTO_INCREMENT,
  `content_id` int DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `content_type_id` int DEFAULT NULL,
  `area_code` int DEFAULT NULL,
  `si_gun_gu_code` int DEFAULT NULL,
  `first_image1` varchar(100) DEFAULT NULL,
  `first_image2` varchar(100) DEFAULT NULL,
  `map_level` int DEFAULT NULL,
  `latitude` decimal(20,17) DEFAULT NULL,
  `longitude` decimal(20,17) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `addr2` varchar(100) DEFAULT NULL,
  `homepage` varchar(1000) DEFAULT NULL,
  `overview` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id`),
  KEY `attractions_si_gun_gu_to guguns_code_fk_idx` (`si_gun_gu_code`),
  KEY `attractions_area_to_sidos_sido_fk_idx` (`area_code`)
) ENGINE=InnoDB AUTO_INCREMENT=107559 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
comment '명소정보테이블';


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
  `member_id` VARCHAR(50) NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `review_id` (`review_id` ASC),
  INDEX `member_id` (`member_id` ASC),
  CONSTRAINT `comments_ibfk_1`
    FOREIGN KEY (`review_id`)
    REFERENCES `tripon`.`reviews` (`id`),
  CONSTRAINT `comments_ibfk_2`
    FOREIGN KEY (`member_id`)
    REFERENCES `tripon`.`members` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tripon`.`custom_attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripon`.`custom_attractions`;
CREATE TABLE `tripon`.`custom_attractions` (
  `id`               INT               NOT NULL AUTO_INCREMENT,
  `title`            VARCHAR(500)      DEFAULT NULL,
  `content_type_id`  INT               DEFAULT NULL,
  `area_code`        INT               DEFAULT NULL,
  `si_gun_gu_code`   INT               DEFAULT NULL,
  `first_image1`     VARCHAR(100)      DEFAULT NULL,
  `addr1`            VARCHAR(100)      DEFAULT NULL,
  `latitude`         DECIMAL(20,17)    DEFAULT NULL,
  `longitude`        DECIMAL(20,17)    DEFAULT NULL,
  `created_at`       DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`       DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `custom_attractions_typeid_to_types_typeid_fk_idx` (`content_type_id`),
  KEY `custom_attractions_si_gun_gu_to_guguns_code_fk_idx` (`si_gun_gu_code`),
  KEY `custom_attractions_area_to_sidos_sido_fk_idx`  (`area_code`)
) ENGINE=InnoDB
  AUTO_INCREMENT=500000
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;



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
  `order_number` INT NOT NULL,
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

-- -----------------------------------------------------
-- View
-- -----------------------------------------------------
CREATE VIEW `tripon`.`unified_attractions_view` AS
SELECT
    no             AS id,
    'attractions'  AS source,
    title,
    area_code,
    si_gun_gu_code,
    latitude,
    longitude,
    first_image1,
    addr1,
    content_type_id
FROM attractions

UNION ALL

SELECT
    id             AS id,
    'custom'       AS source,
    title,
    area_code,
    si_gun_gu_code,
    latitude,
    longitude,
    first_image1,
    addr1,
    content_type_id
FROM custom_attractions;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into members (email, name, password, role) values ('admin@ssafy.com', 'admin', '1234', 'ADMIN');
insert into plans (email, title, start_date, end_date, memo) 
values ('admin@ssafy.com', 'admin', '2025-04-01', '2025-04-04', '첫번째 계획!');

select * from plans;

ALTER TABLE members
  MODIFY COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE comments MODIFY parent_id INT NULL;

ALTER TABLE reviews
ADD COLUMN thumbnail_original VARCHAR(500) NULL DEFAULT NULL,
ADD COLUMN thumbnail_url VARCHAR(500) NULL DEFAULT NULL;

CREATE TABLE IF NOT EXISTS materialized_union_attractions (
  id               INT             NOT NULL,
  source           ENUM('attractions','custom') NOT NULL,
  title            VARCHAR(500)    NULL,
  area_code        INT             NULL,
  si_gun_gu_code   INT             NULL,
  latitude         DECIMAL(20,17)  NULL,
  longitude        DECIMAL(20,17)  NULL,
  first_image1     VARCHAR(100)    NULL,
  addr1            VARCHAR(100)    NULL,
  content_type_id  INT             NULL,
  PRIMARY KEY (source, id),
  INDEX idx_area      (area_code),
  INDEX idx_sigungu   (si_gun_gu_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE TABLE materialized_union_attractions;
INSERT INTO materialized_union_attractions
SELECT
  no           AS id,
  'attractions' AS source,
  title,
  area_code,
  si_gun_gu_code,
  latitude,
  longitude,
  first_image1,
  addr1,
  content_type_id
FROM attractions
UNION ALL
SELECT
  id            AS id,
  'custom'      AS source,
  title,
  area_code,
  si_gun_gu_code,
  latitude,
  longitude,
  first_image1,
  addr1,
  content_type_id
FROM custom_attractions;


DELIMITER $$

-- INSERT
CREATE TRIGGER trg_attractions_ai
AFTER INSERT ON attractions
FOR EACH ROW
BEGIN
  INSERT INTO materialized_union_attractions
    (id, source, title, area_code, si_gun_gu_code, latitude, longitude, first_image1, addr1, content_type_id)
  VALUES
    (NEW.no, 'attractions', NEW.title, NEW.area_code, NEW.si_gun_gu_code, NEW.latitude, NEW.longitude, NEW.first_image1, NEW.addr1, NEW.content_type_id)
  ON DUPLICATE KEY UPDATE
    title           = NEW.title,
    area_code       = NEW.area_code,
    si_gun_gu_code  = NEW.si_gun_gu_code,
    latitude        = NEW.latitude,
    longitude       = NEW.longitude,
    first_image1    = NEW.first_image1,
    addr1           = NEW.addr1,
    content_type_id = NEW.content_type_id;
END$$

-- UPDATE
CREATE TRIGGER trg_attractions_au
AFTER UPDATE ON attractions
FOR EACH ROW
BEGIN
  UPDATE materialized_union_attractions
  SET
    title           = NEW.title,
    area_code       = NEW.area_code,
    si_gun_gu_code  = NEW.si_gun_gu_code,
    latitude        = NEW.latitude,
    longitude       = NEW.longitude,
    first_image1    = NEW.first_image1,
    addr1           = NEW.addr1,
    content_type_id = NEW.content_type_id
  WHERE source = 'attractions' AND id = OLD.no;
END$$

-- DELETE
CREATE TRIGGER trg_attractions_ad
AFTER DELETE ON attractions
FOR EACH ROW
BEGIN
  DELETE FROM materialized_union_attractions
  WHERE source = 'attractions' AND id = OLD.no;
END$$

-- custom_attractions 쪽 트리거 (source='custom')
CREATE TRIGGER trg_custom_ai
AFTER INSERT ON custom_attractions
FOR EACH ROW
BEGIN
  INSERT INTO materialized_union_attractions
    (id, source, title, area_code, si_gun_gu_code, latitude, longitude, first_image1, addr1, content_type_id)
  VALUES
    (NEW.id, 'custom', NEW.title, NEW.area_code, NEW.si_gun_gu_code, NEW.latitude, NEW.longitude, NEW.first_image1, NEW.addr1, NEW.content_type_id)
  ON DUPLICATE KEY UPDATE
    title           = NEW.title,
    area_code       = NEW.area_code,
    si_gun_gu_code  = NEW.si_gun_gu_code,
    latitude        = NEW.latitude,
    longitude       = NEW.longitude,
    first_image1    = NEW.first_image1,
    addr1           = NEW.addr1,
    content_type_id = NEW.content_type_id;
END$$

CREATE TRIGGER trg_custom_au
AFTER UPDATE ON custom_attractions
FOR EACH ROW
BEGIN
  UPDATE materialized_union_attractions
  SET
    title           = NEW.title,
    area_code       = NEW.area_code,
    si_gun_gu_code  = NEW.si_gun_gu_code,
    latitude        = NEW.latitude,
    longitude       = NEW.longitude,
    first_image1    = NEW.first_image1,
    addr1           = NEW.addr1,
    content_type_id = NEW.content_type_id
  WHERE source = 'custom' AND id = OLD.id;
END$$

CREATE TRIGGER trg_custom_ad
AFTER DELETE ON custom_attractions
FOR EACH ROW
BEGIN
  DELETE FROM materialized_union_attractions
  WHERE source = 'custom' AND id = OLD.id;
END$$

DELIMITER ;

-- 도전...!!
ALTER TABLE tripon.plans
  ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

insert into members (email, name, password, role) values ('admin@ssafy.com', 'admin', '1234', 'ADMIN');
insert into plans (email, title, start_date, end_date, memo)
values ('admin@ssafy.com', 'admin', '2025-04-01', '2025-04-04', '첫번째 계획!');

select * from plans;

CREATE TABLE tripon.plan_events (
  id         BIGINT      NOT NULL AUTO_INCREMENT,
  plan_id    INT         NOT NULL,
  email      VARCHAR(50) NOT NULL,
  event_type VARCHAR(50) NOT NULL,
  payload    JSON        NOT NULL,
  created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX idx_plan (plan_id)
);

ALTER TABLE members
  MODIFY COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE comments MODIFY parent_id INT NULL;

ALTER TABLE reviews
ADD COLUMN thumbnail_original VARCHAR(500) NULL DEFAULT NULL,
ADD COLUMN thumbnail_url VARCHAR(500) NULL DEFAULT NULL;

