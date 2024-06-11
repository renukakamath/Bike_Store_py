/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - bike_store
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bike_store` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bike_store`;

/*Table structure for table `bike` */

DROP TABLE IF EXISTS `bike`;

CREATE TABLE `bike` (
  `bike_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL,
  `bike` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `color` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bike_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `bike` */

insert  into `bike`(`bike_id`,`shop_id`,`bike`,`model`,`brand`,`color`,`image`,`amount`) values 
(1,1,'bike','model','brands','yelloww','static/image/83f04e55-2a02-4570-83db-8b2bbcbe5c7dbike.jpg','1000');

/*Table structure for table `bike_request` */

DROP TABLE IF EXISTS `bike_request`;

CREATE TABLE `bike_request` (
  `bike_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `bike_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bike_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `bike_request` */

insert  into `bike_request`(`bike_request_id`,`user_id`,`bike_id`,`date`,`status`) values 
(1,1,1,'2023-02-13','Accept'),
(2,1,1,'2023-02-13','Accept');

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `blog_id` int(11) NOT NULL AUTO_INCREMENT,
  `bike_id` int(11) DEFAULT NULL,
  `blog` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`blog_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `blog` */

insert  into `blog`(`blog_id`,`bike_id`,`blog`,`description`) values 
(1,1,'sdfgj','ertyuigfdd gh'),
(2,2,'qwerty','wdfghjkl;'),
(3,2,'userrr','9876543210');

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `comments_id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`comments_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `comments` */

insert  into `comments`(`comments_id`,`blog_id`,`comments`,`date`) values 
(1,1,'gookn','2023-02-13');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'fhjj','pending','2023-02-14');

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) DEFAULT NULL,
  `images` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `image` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'u1','u1','customer'),
(8,'shop','shop','shop'),
(2,'sha','spa','spareparts'),
(4,'sdfgh','sdfg','sdfgh'),
(9,'sp','sp','spareparts'),
(10,'shop1','shop1','shop'),
(3,'admin','admin','admin');

/*Table structure for table `order_details` */

DROP TABLE IF EXISTS `order_details`;

CREATE TABLE `order_details` (
  `odetails_id` int(11) NOT NULL AUTO_INCREMENT,
  `omaster_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`odetails_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `order_details` */

insert  into `order_details`(`odetails_id`,`omaster_id`,`product_id`,`amount`,`quantity`) values 
(1,1,1,'6000','3'),
(2,2,1,'4000','2');

/*Table structure for table `order_master` */

DROP TABLE IF EXISTS `order_master`;

CREATE TABLE `order_master` (
  `omaster_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `spareparts_id` int(11) DEFAULT NULL,
  `total` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`omaster_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `order_master` */

insert  into `order_master`(`omaster_id`,`user_id`,`spareparts_id`,`total`,`date`,`status`) values 
(1,1,1,'6000','2023-02-13','Accept'),
(2,1,1,'4000','2023-02-13','pending');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `payed_for_id` int(11) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `payment_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`user_id`,`payed_for_id`,`type`,`amount`,`payment_date`) values 
(1,1,2,'bike request','500','2023-02-13'),
(2,1,2,'bike request','500','2023-02-13'),
(3,1,1,'product booking','6000','2023-02-13'),
(4,1,1,'service request','null','2023-02-14'),
(5,1,1,'service request','null','2023-02-14'),
(6,1,1,'service request','null','2023-02-14'),
(7,1,1,'service request','null','2023-02-14'),
(8,1,1,'service request','null','2023-02-14'),
(9,1,1,'service request','null','2023-02-14'),
(10,1,1,'service request','null','2023-02-14'),
(17,1,2,'Rent request','1000','2023-02-14'),
(13,1,8,'Rent request','1000','2023-02-14'),
(14,1,8,'Rent request','1000','2023-02-14'),
(15,1,8,'Rent request','1000','2023-02-14'),
(16,1,1,'Rent request','1000','2023-02-14');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `spareparts_id` int(11) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `rate` varchar(100) DEFAULT NULL,
  `stock` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`spareparts_id`,`product_name`,`rate`,`stock`,`image`) values 
(1,1,'shoe','2000','500','static/image1e11bc3f-d15b-4c15-9a01-9f0e8bffa6b7download (1) - Copy.jfif');

/*Table structure for table `rent_amount` */

DROP TABLE IF EXISTS `rent_amount`;

CREATE TABLE `rent_amount` (
  `rent_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `rent_amount` */

insert  into `rent_amount`(`rent_id`,`vehicle_id`,`amount`,`date`) values 
(1,1,'500','2023-02-14'),
(2,1,'500','2023-02-14'),
(3,2,'500','2023-02-14'),
(4,2,'','2023-02-14'),
(5,1,'1000','2023-02-14'),
(6,1,'1000','2023-02-14'),
(7,3,'1000','2023-02-14'),
(8,3,'1000','2023-02-14'),
(9,1,'1000','2023-02-14'),
(10,NULL,NULL,NULL);

/*Table structure for table `rent_request` */

DROP TABLE IF EXISTS `rent_request`;

CREATE TABLE `rent_request` (
  `rent_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `rent_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rent_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `rent_request` */

insert  into `rent_request`(`rent_request_id`,`rent_id`,`user_id`,`date`,`status`) values 
(1,7,1,'2023-02-14','pending'),
(2,8,1,'2023-02-14','Paid');

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_type_id` int(11) DEFAULT NULL,
  `service` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `rate` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `service` */

insert  into `service`(`service_id`,`service_type_id`,`service`,`description`,`rate`) values 
(1,1,'ser','sdfghjk sadfghj','500'),
(2,1,'vendor','02345678907','5000');

/*Table structure for table `service_request` */

DROP TABLE IF EXISTS `service_request`;

CREATE TABLE `service_request` (
  `service_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `timeslot_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `service_request` */

insert  into `service_request`(`service_request_id`,`user_id`,`timeslot_id`,`date`,`status`) values 
(1,1,1,'2023-02-14','Reject');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `service_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `service_type` */

insert  into `service_type`(`service_type_id`,`service_type`) values 
(1,'service'),
(2,'staff'),
(3,'vendor');

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `shop_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `shop` */

insert  into `shop`(`shop_id`,`login_id`,`shop_name`,`place`,`phone`,`email`) values 
(1,8,'shop','erna','1234567891','shop1@gmail.com'),
(2,10,'user','kerala','2345678907','student@gmail.com');

/*Table structure for table `spareparts` */

DROP TABLE IF EXISTS `spareparts`;

CREATE TABLE `spareparts` (
  `spareparts_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`spareparts_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `spareparts` */

insert  into `spareparts`(`spareparts_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,2,'spare','fghrtyh','erthy','12345678','dfg@gmail.com'),
(2,9,'user','qwerty','kerala','2345678907','student@gmail.com');

/*Table structure for table `timeslot` */

DROP TABLE IF EXISTS `timeslot`;

CREATE TABLE `timeslot` (
  `timeslot_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`timeslot_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `timeslot` */

insert  into `timeslot`(`timeslot_id`,`service_id`,`date`,`time`) values 
(1,1,'20-02-2002','10:20');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,1,'u','u','place','153489786','u@gmail.com'),
(2,4,'sdfghj','ertyuio','fghjkwe','1234567823','fghjk');

/*Table structure for table `vehicle_details` */

DROP TABLE IF EXISTS `vehicle_details`;

CREATE TABLE `vehicle_details` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `vehicle` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `color` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `vehicle_details` */

insert  into `vehicle_details`(`vehicle_id`,`user_id`,`vehicle`,`model`,`brand`,`color`) values 
(1,1,'vehi','hsjjshs','bdhshs','vdhsh'),
(2,1,'vehi','hsjjshs','bdhshs','vdhsh'),
(3,2,'vehile','model1','fwghdjhwdw','red');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
