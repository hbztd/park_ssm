/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.12 : Database - parking
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`parking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `parking`;

/*Table structure for table `card` */

DROP TABLE IF EXISTS `card`;

CREATE TABLE `card` (
  `card_id` varchar(50) NOT NULL,
  `seat_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_gender` varchar(1) NOT NULL,
  `user_addr` varchar(50) NOT NULL,
  `car_num` varchar(50) NOT NULL,
  `card_status` int(10) DEFAULT '0',
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `card_id` (`card_id`,`seat_id`,`user_name`,`car_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `card` */

insert  into `card`(`card_id`,`seat_id`,`user_name`,`user_gender`,`user_addr`,`car_num`,`card_status`) values ('C1545038775480','S1545038724458','李四','男','湖北襄阳','鄂F12345',0),('C1545109574836','S1545108195506','张三','男','湖北襄阳','鄂F12346',0),('C1545109632251','S1545109292606','李四','男','湖北襄阳','鄂F12347',0),('C1545109690260','S1545109301765','王小丽','女','湖北襄阳','',0),('C1545109763231','S1545109341384','沈小红','女','湖北襄阳','鄂F32132',0),('C1545109820476','S1545109391154','王总','男','湖北襄阳','鄂F88888',0),('C1545109840309','S1545109400022','李总','男','湖北襄阳','鄂F77777',0),('C1545109860511','S1545109410756','张总','男','湖北襄阳','鄂F66666',0);

/*Table structure for table `fixed` */

DROP TABLE IF EXISTS `fixed`;

CREATE TABLE `fixed` (
  `fixed_id` varchar(50) NOT NULL,
  `card_id` varchar(50) NOT NULL,
  `entry_date` date NOT NULL,
  `entry_time` time NOT NULL,
  `out_date` date DEFAULT NULL,
  `out_time` time DEFAULT NULL,
  `fixed_status` int(10) NOT NULL,
  PRIMARY KEY (`fixed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `fixed` */

insert  into `fixed`(`fixed_id`,`card_id`,`entry_date`,`entry_time`,`out_date`,`out_time`,`fixed_status`) values ('F1545109923525','C1545038775480','2018-12-18','13:12:03','2018-12-18','13:14:17',0),('F1545109937196','C1545109574836','2018-12-18','13:12:17','2018-12-18','13:37:32',0),('F1545110030449','C1545109820476','2018-12-18','13:13:50','2018-12-18','13:37:34',0),('F1545110035545','C1545109860511','2018-12-18','13:13:55','2018-12-18','13:37:36',0),('F1545110041419','C1545109840309','2018-12-18','13:14:01','2018-12-18','13:37:38',0);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`id`,`url`,`name`) values (1,'/card/add.do','增加车主'),(2,'/card/delete.do','删除车主'),(3,'/card/update.do','更新车主'),(4,'/card/find.do','查找车主信息'),(5,'/fixed/entry.do','进入停车场'),(6,'/fixed/out.do','退出停车场'),(7,'/fixed/delete.do','删除停车记录'),(8,'/fixed/find.do','查找所有停车记录'),(9,'/fixed/findEntry.do','查找正在停车的记录'),(10,'/seat/add.do','增加车位'),(11,'/seat/delete.do','删除车位'),(12,'/seat/update.do','更新车位'),(13,'/seat/find.do','查找车位信息'),(14,'/temp/entry.do','进入停车场'),(15,'/temp/out.do','退出停车场'),(16,'/temp/delete.do','删除停车记录'),(17,'/temp/find.do','查找所有停车记录'),(18,'/temp/findEntry.do','查找正在停车的记录');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`) values ('r001','超级管理员'),('r002','普通管理员');

/*Table structure for table `seat` */

DROP TABLE IF EXISTS `seat`;

CREATE TABLE `seat` (
  `seat_id` varchar(50) NOT NULL,
  `seat_num` varchar(50) NOT NULL,
  `seat_section` varchar(50) NOT NULL,
  `seat_state` int(11) NOT NULL,
  `seat_tag` varchar(50) DEFAULT NULL,
  `seat_type` int(10) NOT NULL,
  PRIMARY KEY (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `seat` */

insert  into `seat`(`seat_id`,`seat_num`,`seat_section`,`seat_state`,`seat_tag`,`seat_type`) values ('S1545038724458','A1001','A区',1,'',2),('S1545108195506','A1002','A区',1,'',2),('S1545109292606','A1003','A区',1,'',2),('S1545109301765','A1004','A区',1,'',2),('S1545109306783','A1005','A区',0,'',1),('S1545109311052','A1006','A区',0,'',1),('S1545109319744','A1007','A区',0,'',1),('S1545109324414','A1008','A区',0,'',1),('S1545109328663','A1009','A区',0,'',1),('S1545109341384','B1001','B区',1,'',2),('S1545109345562','B1002','B区',0,'',1),('S1545109349702','B1003','B区',0,'',1),('S1545109355181','B1004','B区',0,'',1),('S1545109359481','B1005','B区',0,'',1),('S1545109363702','B1006','B区',0,'',1),('S1545109368311','B1007','B区',0,'',1),('S1545109372596','B1008','B区',0,'',1),('S1545109391154','VIP1001','B区',1,'王总',2),('S1545109400022','VIP1002','B区',1,'李总',2),('S1545109410756','VIP1003','B区',1,'张总',2);

/*Table structure for table `temp` */

DROP TABLE IF EXISTS `temp`;

CREATE TABLE `temp` (
  `temp_id` varchar(50) NOT NULL,
  `card_id` varchar(50) NOT NULL,
  `car_num` varchar(50) NOT NULL,
  `entry_date` date NOT NULL,
  `entry_time` time NOT NULL,
  `out_date` date DEFAULT NULL,
  `out_time` time DEFAULT NULL,
  `temp_money` decimal(20,2) DEFAULT NULL,
  `temp_status` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`temp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `temp` */

insert  into `temp`(`temp_id`,`card_id`,`car_num`,`entry_date`,`entry_time`,`out_date`,`out_time`,`temp_money`,`temp_status`) values ('T1545109957935','鄂F98765','鄂F98765','2018-12-18','13:12:37','2018-12-18','13:37:52','1.25',0),('T1545109988350','鄂F65478','鄂F65478','2018-12-18','13:13:08','2018-12-18','13:37:55','1.20',0),('T1545109992760','鄂F65479','鄂F65479','2018-12-18','13:13:12','2018-12-18','13:37:57','1.20',0),('T1545109997181','鄂F65449','鄂F65449','2018-12-18','13:13:17','2018-12-18','13:37:59','1.20',0),('T1545110002086','鄂F62449','鄂F62449','2018-12-18','13:13:22','2018-12-18','13:38:01','1.20',0),('T1545110007351','鄂F62459','鄂F62459','2018-12-18','13:13:27','2018-12-18','13:38:03','1.20',0),('T1545110011475','鄂F62419','鄂F62419','2018-12-18','13:13:31','2018-12-18','13:38:05','1.20',0),('T1545110016136','鄂F62499','鄂F62499','2018-12-18','13:13:36','2018-12-18','13:38:07','1.20',0);

/*Table structure for table `u_permission` */

DROP TABLE IF EXISTS `u_permission`;

CREATE TABLE `u_permission` (
  `uid` varchar(50) NOT NULL,
  `pid` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `u_permission` */

insert  into `u_permission`(`uid`,`pid`) values ('xiaoman',1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `real_name` varchar(50) NOT NULL,
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`account`,`role_id`,`user_name`,`real_name`,`user_pwd`,`user_phone`) values ('xiaoman','r002','小漫','张小漫','09DC9AE3AF6E1C10D952DBD9C62E3C61','18871012713');

/*Table structure for table `v_card` */

DROP TABLE IF EXISTS `v_card`;

/*!50001 DROP VIEW IF EXISTS `v_card` */;
/*!50001 DROP TABLE IF EXISTS `v_card` */;

/*!50001 CREATE TABLE  `v_card`(
 `card_id` varchar(50) ,
 `seat_id` varchar(50) ,
 `user_name` varchar(50) ,
 `user_gender` varchar(1) ,
 `user_addr` varchar(50) ,
 `car_num` varchar(50) ,
 `seat_num` varchar(50) ,
 `card_status` int(10) 
)*/;

/*Table structure for table `v_fixed` */

DROP TABLE IF EXISTS `v_fixed`;

/*!50001 DROP VIEW IF EXISTS `v_fixed` */;
/*!50001 DROP TABLE IF EXISTS `v_fixed` */;

/*!50001 CREATE TABLE  `v_fixed`(
 `fixed_id` varchar(50) ,
 `card_id` varchar(50) ,
 `entry_date` date ,
 `entry_time` time ,
 `out_date` date ,
 `out_time` time ,
 `car_num` varchar(50) ,
 `user_name` varchar(50) ,
 `status` int(10) 
)*/;

/*Table structure for table `v_user` */

DROP TABLE IF EXISTS `v_user`;

/*!50001 DROP VIEW IF EXISTS `v_user` */;
/*!50001 DROP TABLE IF EXISTS `v_user` */;

/*!50001 CREATE TABLE  `v_user`(
 `account` varchar(50) ,
 `role_id` varchar(50) ,
 `user_name` varchar(50) ,
 `real_name` varchar(50) ,
 `user_pwd` varchar(255) ,
 `user_phone` varchar(50) ,
 `role_name` varchar(50) 
)*/;

/*View structure for view v_card */

/*!50001 DROP TABLE IF EXISTS `v_card` */;
/*!50001 DROP VIEW IF EXISTS `v_card` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_card` AS select `card`.`card_id` AS `card_id`,`card`.`seat_id` AS `seat_id`,`card`.`user_name` AS `user_name`,`card`.`user_gender` AS `user_gender`,`card`.`user_addr` AS `user_addr`,`card`.`car_num` AS `car_num`,`seat`.`seat_num` AS `seat_num`,`card`.`card_status` AS `card_status` from (`card` join `seat` on((`card`.`seat_id` = `seat`.`seat_id`))) */;

/*View structure for view v_fixed */

/*!50001 DROP TABLE IF EXISTS `v_fixed` */;
/*!50001 DROP VIEW IF EXISTS `v_fixed` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_fixed` AS select `fixed`.`fixed_id` AS `fixed_id`,`fixed`.`card_id` AS `card_id`,`fixed`.`entry_date` AS `entry_date`,`fixed`.`entry_time` AS `entry_time`,`fixed`.`out_date` AS `out_date`,`fixed`.`out_time` AS `out_time`,`card`.`car_num` AS `car_num`,`card`.`user_name` AS `user_name`,`fixed`.`fixed_status` AS `status` from (`fixed` join `card` on((`fixed`.`card_id` = `card`.`card_id`))) */;

/*View structure for view v_user */

/*!50001 DROP TABLE IF EXISTS `v_user` */;
/*!50001 DROP VIEW IF EXISTS `v_user` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_user` AS select `user`.`account` AS `account`,`user`.`role_id` AS `role_id`,`user`.`user_name` AS `user_name`,`user`.`real_name` AS `real_name`,`user`.`user_pwd` AS `user_pwd`,`user`.`user_phone` AS `user_phone`,`role`.`role_name` AS `role_name` from (`user` join `role` on((`user`.`role_id` = `role`.`role_id`))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
