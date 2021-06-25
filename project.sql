CREATE DATABASE SEMESTERPROJECT;
use SEMESTERPROJECT;


CREATE TABLE adminAcc(
	id int auto_increment primary key,
    fullName varchar(100) unique,
    passcode varchar(100),
    isSuper bool
);

CREATE TABLE dish(
	id int auto_increment primary key,
	dishName varchar(50),
    dishDesc varchar(100),
    price float,
    dishImg varchar(255)
);
delete FROM dish WHERE id=16 ;

INSERT INTO adminAcc(fullName,passcode,isSuper) VALUES("admin","12345",true);
INSERT INTO adminAcc(fullName,passcode,isSuper) VALUES("daud","12345",false);
SELECT * FROM adminAcc WHERE fullName='daud' AND passcode='12345';
SELECT * FROM adminAcc ;
SELECT * FROM dish ;
SHOW TABLES;