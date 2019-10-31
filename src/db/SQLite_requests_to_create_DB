CREATE TABLE `organization_data` (
  `id_organization` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `organization_name` varchar(256) NOT NULL);
  
INSERT INTO `organization_data` VALUES (1,'ДЧС по Омской губернии');

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  role_code varchar(10) NOT NULL,
  role_name varchar(45) NOT NULL,
  PRIMARY KEY(`role_code`)
  );  

CREATE TABLE `employee_divisions` (
  `division_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `division_code` varchar(10) NOT NULL,
  `division_description` varchar(256) DEFAULT NULL  
);

INSERT INTO `employee_divisions` VALUES (2,'УИБ','Управление информационной безопасности'),(3,'АХО','Управление административно-хозяйственного обеспечения'),(4,'СТО','Управление системно-технического обеспечения'),(5,'СИС','Управление сопровождения информационных систем'),(6,'РУК','Руководство'),(7,'ДИСП','Дипетчерская служба'),(8,'СБР','Служба быстрого реагирования');

CREATE TABLE `users` (
  `user_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `user_role` varchar(10) NOT NULL,
  `user_status` tinyint(4) NOT NULL,
  `user_undeletable` tinyint(4) NOT NULL DEFAULT '0'  
);

INSERT INTO `users` VALUES (8,'ADMIN','ADMINPASSWORD','ADMIN',1,1),(9,'GUEST','guest','GUEST',1,1),(10,'Stas','123','USER',1,0),(11,'Wesley','123','ADMIN',1,0),(12,'Nurik','123','User',0,0);

CREATE TABLE `employee_positions` (
  `position_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `position_name` varchar(128) DEFAULT NULL);
  
INSERT INTO `employee_positions` VALUES (10,'Генерал-лейтенант'),(9,'Генерал-майор'),(11,'Генерал-полковник'),(5,'Капитан'),(3,'Лейтенант'),(6,'Майор'),(2,'Младший лейтенант'),(7,'Подполковник'),(8,'Полковник'),(1,'Сержант'),(4,'Старший лейтенант');

CREATE TABLE `employees` (
  `employee_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `employee_last_name` varchar(45) NOT NULL,
  `employee_name` varchar(45) NOT NULL,
  `employee_middle_name` varchar(45) DEFAULT NULL,
  `employee_position_id` int(11) NOT NULL,
  `employee_division_id` int(11) NOT NULL,
  `employee_user_id` int(11) DEFAULT NULL);
  
INSERT INTO `employees` VALUES (3,'Савченко','Станислав','Олегович',2,2,10),(4,'Савченко','Василий',NULL,4,5,11),(5,'Кенжекеев','Нурлан','Кабылбайулы',3,3,12),(6,'Калелов','Ораз',NULL,1,4,NULL);

CREATE TABLE `technic_types` (
  `technic_type_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `technic_description` varchar(120) NOT NULL);
  
INSERT INTO `technic_types` VALUES (1,'Системный блок'),(2,'Монитор'),(3,'Клавиатура'),(4,'Манипулятор \"мышь\"'),(5,'Принтер'),(6,'Сканер'),(7,'МФУ'),(8,'Коммуникационное оборудование'),(9,'Прочее'),(10,'Телефония');

CREATE TABLE `technic_statuses` (
  `technic_status_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `technic_status_description` varchar(128) NOT NULL);
  
INSERT INTO `technic_statuses` VALUES (1,'в работе'),(2,'готово к работе'),(3,'неисправно'),(4,'списано');

CREATE TABLE `technic` (
  `technic_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `technic_name` varchar(128) NOT NULL,
  `technic_details` varchar(256) DEFAULT NULL,
  `technic_status_id` int(11) NOT NULL,
  `technic_type_id` int(11) NOT NULL,
  `technic_owner` int(11) NOT NULL,
  `technic_repairer` int(11) DEFAULT NULL);
  
INSERT INTO `technic` VALUES (1,'Acer 3120pro','Системный блок Acer',1,1,3,3),(2,'Philips 243S','LED IPS Монитор',1,2,4,3),(3,'Cisco 7911','IP телефон',3,10,5,3),(4,'Genius XX','Колонки',4,9,6,3),(5,'Delux','Наушники',1,9,5,3),(6,'Dell E2213Hb','Монитор Dell',2,2,4,4);

CREATE TABLE `requests` (
  `request_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  `request_technic_id` int(11) NOT NULL,
  `request_open_date` datetime NOT NULL,
  `request_close_date` datetime DEFAULT NULL,
  `request_problem_description` varchar(512) NOT NULL,
  `request_decision_description` varchar(512) DEFAULT NULL,
  `request_status` tinyint(4) NOT NULL DEFAULT '0',
  `request_author_id` int(11) NOT NULL,
  `request_closer_id` int(11) DEFAULT NULL);
  
INSERT INTO `requests` VALUES (1,1,'2019-10-17 10:15:00',NULL,'Не включается, идёт дым',NULL,0,10,NULL),(2,2,'2019-10-14 13:25:00','2019-10-15 09:22:00','Изображение плывёт','Изображение приплыло',1,11,12),(3,5,'2019-10-11 11:32:00',NULL,'Оборван провод',NULL,0,10,NULL),(4,3,'2019-10-21 11:21:00','2019-10-11 17:32:00','Нехорошо пахнет','Побрызгали дезодорантом',1,12,11);