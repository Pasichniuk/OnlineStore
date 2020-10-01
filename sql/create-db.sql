DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS order;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS store_user;
DROP TABLE IF EXISTS role;

-- CREATE DATABASE `online_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `role` (
  `role_id` int NOT NULL,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO role VALUES(1, 'ROLE_USER');
INSERT INTO role VALUES(2, 'ROLE_ADMIN');

CREATE TABLE `store_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_login` varchar(45) NOT NULL,
  `user_role` int NOT NULL,
  `block_status` enum('UNBLOCKED','BLOCKED') NOT NULL DEFAULT 'UNBLOCKED',
  `user_password` varbinary(255) DEFAULT NULL,
  `user_name` varchar(90) NOT NULL,
  `user_name_ru` varchar(90) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_login_UNIQUE` (`user_login`),
  KEY `role_pk_idx` (`user_role`),
  CONSTRAINT `role_pk` FOREIGN KEY (`user_role`) REFERENCES `role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO store_user(user_login, user_role, user_password, user_name, user_name_ru) VALUES('John', 1, aes_encrypt('pass123', 'password'), 'John Parker', 'Джон Паркер');
INSERT INTO store_user(user_login, user_role, user_password, user_name, user_name_ru) VALUES('Mark', 1, aes_encrypt('qwerty123', 'password'), 'Mark Polo', 'Марк Поло');
INSERT INTO store_user(user_login, user_role, user_password, user_name, user_name_ru) VALUES('Fred', 1, aes_encrypt('123456', 'password'), 'Fred Smith', 'Фред Смит');
INSERT INTO store_user(user_login, user_role, user_password, user_name, user_name_ru) VALUES('Ivan', 2, aes_encrypt('admin123', 'password'), 'Ivan Ivanov', 'Иван Иванов');

CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  `category_name_ru` varchar(90) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name_UNIQUE` (`category_name`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  UNIQUE KEY `category_name_ru_UNIQUE` (`category_name_ru`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO category(category_name, category_name_ru) VALUES('Phones', 'Телефоны');
INSERT INTO category(category_name, category_name_ru) VALUES('Laptops', 'Ноутбуки');
INSERT INTO category(category_name, category_name_ru) VALUES('Headphones', 'Наушники');
INSERT INTO category(category_name, category_name_ru) VALUES('Home appliances', 'Бытовая техника');

CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `category` int NOT NULL,
  `price` float NOT NULL,
  `addition_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_id_UNIQUE` (`product_id`),
  UNIQUE KEY `product_name_UNIQUE` (`product_name`),
  KEY `category_pk_idx` (`category`),
  CONSTRAINT `category_pk` FOREIGN KEY (`category`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO product(product_name, category, price) VALUES('iPhone XR', 1, 700.7);
INSERT INTO product(product_name, category, price) VALUES('iPhone 11', 1, 800.5);
INSERT INTO product(product_name, category, price) VALUES('Macbook Air', 2, 1000.9);
INSERT INTO product(product_name, category, price) VALUES('Macbook Pro', 2, 1200.2);
INSERT INTO product(product_name, category, price) VALUES('AirPods', 3, 200.5);
INSERT INTO product(product_name, category, price) VALUES('AirPods Pro', 3, 400.4);
INSERT INTO product(product_name, category, price) VALUES('HomePod', 4, 900.3);

CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_status` enum('REGISTERED','PAID','CANCELLED') NOT NULL DEFAULT 'REGISTERED',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  KEY `user_fk_idx` (`user_id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `store_user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO order (user_id) VALUES (1);
INSERT INTO order (user_id) VALUES (2);
INSERT INTO order (user_id) VALUES (2);
INSERT INTO order (user_id) VALUES (3);

CREATE TABLE `order_product` (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  KEY `order_fk_idx` (`order_id`),
  KEY `product_fk_idx` (`product_id`),
  CONSTRAINT `order_fk` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `product_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO order_product VALUES (1, 1);
INSERT INTO order_product VALUES (1, 3);
INSERT INTO order_product VALUES (2, 4);
INSERT INTO order_product VALUES (3, 2);
INSERT INTO order_product VALUES (3, 5);
INSERT INTO order_product VALUES (3, 7);
INSERT INTO order_product VALUES (4, 6);