drop table animal;
drop table crop;
drop table transaction;
drop table product;
drop table farmland;
drop table farmer;
drop table broker;


create table farmer
	(Farmer_ID int not null PRIMARY KEY,
	Name char(45) not null);

grant select on farmer to public;


    create table farmland (
	Farm_ID int not null PRIMARY KEY,
	Area_Capacity float null,
	Farmer_ID int not null,
    province char(45) null,
	foreign key (Farmer_ID) references farmer);

grant select on farmland to public;

create table product
(Product_ID int not null PRIMARY KEY,
Selling_Price float not null,
Unit char(45) null,
Type char(45) null,
quantity int not null,
farmer_id int not null,
 foreign key(farmer_id) references farmer);
grant select on product to public;



create table animal
	(Product_ID int not null PRIMARY KEY,
	CostPerKg float not null);

grant select on animal to public;

create table crop
	(Product_ID int not null PRIMARY KEY,
	CostPerSeedPackage float not null);

grant select on crop to public;

create table broker(
broker_id int not null PRIMARY KEY,
broker_name char(45) not null);

grant select on broker to public;

create table transaction(
transaction_id int not null PRIMARY KEY,
 trans_date date,
 animalNumber int not null,
 cropNumber int not null,
 farmer_id int not null,
 broker_id int not null,
 product_id int not null,
 foreign key (farmer_id) references farmer,
 foreign key (broker_id) references broker,
 foreign key (product_id) references product);

grant select on transaction to public;
-- populate tables--
-- farmer --
insert into farmer
values(001, 'Ed Knorr');

insert into farmer
values(002, 'Raymond Ng');

insert into farmer
values(003, 'Paul Carter');

insert into farmer
values(004, 'Mary Jane');

insert into farmer
values(005, 'Hugh Jackman');

insert into farmer
values(006, 'Emma Stone');

-- farmland --
insert into farmland
values(101, 64.1, 001,'BC');

insert into farmland
values(102, 29.8, 002, 'BC');

insert into farmland
values(103, 10.9, 003, 'QC');

insert into farmland
values(104, 52.3, 004, 'AB');

insert into farmland
values(105, 34.5, 005, 'ON');

insert into farmland
values(106, 49.9, 006, 'QC');

insert into farmland
values(107, 11.9, 006, 'AB');

-- product --

 insert into product
 values(201, 20, 'kg', 'cow',1000,001);

 insert into product
 values(202, 13.1, 'kg', 'chicken',2000,001);

insert into product
values(203, 4.5, 'kg', 'corn',2000,001);

insert into product
values(204, 5, 'kg', 'corn',1500,002);

insert into product
values(205, 12.8, 'kg', 'chicken',1400,
002);

insert into product
values(206, 13.5, 'kg', 'chicken',38,
003);

insert into product
values(207, 18.7, 'kg', 'pig',320,
003);

insert into product
values(208, 19.7, 'kg', 'pig',107,
004);

insert into product
values(209, 3.8, 'kg', 'wheat',700,
004);

insert into product
values(210, 25.3, 'kg', 'lamb',25,
004);

insert into product
values(211, 3.6, 'kg', 'wheat',1000,
005);

insert into product
values(212, 3.9, 'kg', 'wheat',590,
006);

insert into product
values(213, 19.9, 'kg', 'cow',50,
006);

insert into product
values(214, 2.8, 'kg', 'tomato',1300,
006);

insert into product
values(215, 1.5, 'kg', 'potato',360,
006);

-- broker --
insert into broker
values(301, 'Jan Manuch');

insert into broker
values(302, 'Steve Wolfman');

insert into broker
values(303, 'Patrice Belleville');

insert into broker
values(304, 'Alan Hu');

insert into broker
values(305, 'Luke Skywalker');

-- insert into transaction
-- values(100001, 2010/11/30, 12, 0, 001, 301, 201);
--
-- insert into transaction
-- values(100002, 2011/02/05, 0, 11, 002, 301, 204);
--
-- insert into transaction
-- values(100003, 2011/08/03, 34, 0, 002, 302, 205);
--
-- insert into transaction
-- values(100004, 2011/10/30, 0, 10, 001, 302, 203);
--
-- insert into transaction
-- values(100005, 2012/02/03, 100, 0, 004, 304, 208);
--
-- insert into transaction
-- values(100006, 2012/04/23, 60, 0, 003, 303, 206);
--
-- insert into transaction
-- values(100007, 2013/04/23, 0, 400, 004, 305, 209);
--
-- insert into transaction
-- values(100008, 2013/08/23, 0, 50, 005, 303, 211);
--
-- insert into transaction
-- values(100009, 2014/12/23, 43, 0, 006, 305, 213);
--
-- insert into transaction
-- values(100010, 2014/12/30, 43, 0, 002, 304, 205);
--
-- insert into transaction
-- values(100011, 2015/02/23, 0, 22, 006, 302, 214);
--
-- insert into transaction
-- values(100012, 2015/12/20, 43, 0, 001, 302, 202);