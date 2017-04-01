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


-- populate tables--

insert into farmer
values(001, 'Ed Knorr');

insert into farmer
values(002, 'Raymond Ng');

insert into farmer
values(003, 'Paul Carter');

insert into farmland
values(101, 64.1, 001,'BC');

insert into farmland
values(102, 29.8, 002, 'BC');

insert into farmland
values(103, 10.9, 003, 'QC');

grant select on product to public;
 insert into product
 values(201, 12.2, 'kg', 'cow',1000,001);

 insert into product
 values(202, 13.1, 'kg', 'chicken',2000,001);

insert into product
values(203, 123.1, 'kg', 'corn',2000,001);

insert into product
values(204, 123.1, 'kg', 'corn',1500,002);

insert into product
values(205, 13.1, 'kg', 'chicken',1400,
003);


insert into animal
values(201, 9);

insert into animal
values(202, 10);

insert into animal
values(206, 11);

insert into crop
values(203, 100);

insert into crop
values(204, 20);

insert into crop
values(205, 65);

-- insert into broker
-- values(301, 'Jan Manuch');

-- insert into broker
-- values(302, 'Steve Wolfman');

-- insert into broker
-- values(303, 'Patrice Belleville');

-- insert into transaction
-- values(20100, 2010/11/30, 12, 10);

-- insert into transaction
-- values(12932, 2017/02/05, 32, 11);

-- insert into transaction
-- values(00001, 2015/08/03, 34, 23);

-- insert into location
-- values('V6K 1K5', 'Nanaimo', 'British Columbia');

-- insert into location
-- values('M5T 2T4', 'Muskoka', 'Ontario');

-- insert into location
-- values('T3R 2S7', 'Big Lakes', 'Alberta');

-- insert into own
-- values(001, 203);

-- insert into own
-- values(002, 204);

-- insert into own
-- values(003, 205);

-- insert into sell
-- values(20100, 001, 203, 301);

-- insert into sell
-- values(12932, 002, 204, 302);

-- insert into sell
-- values(00001, 003, 205, 303);

-- insert into purchase
-- values(20100, 301);

-- insert into purchase
-- values(12932, 302);

-- insert into purchase
-- values(00001, 303);

-- insert into has
-- values(001, 101);

-- insert into has
-- values(002, 102);

-- insert into has
-- values(003, 103);

-- insert into isin
-- values('V6K 1K5', 101);

-- insert into isin
-- values('M5T 2T4' ,102);

-- insert into isin
-- values('T3R 2S7', 103);

