create table farmer
	(Famer_ID int not null,
	Name char(45) not null,
	primary key (Famer_ID));
 
grant select on farmer to public;
 
create table farmland
	(Farm_ID int not null,
	Area_Capacity float null,
	primary key (Farm_ID));
 
grant select on farmland to public;
 
create table product
	(Product_ID int not null,
	Selling_Price float not null,
	Unit char(45) null,
	Type char(45) null,
	primary key (Product_ID));

grant select on product to public;

create table animal
	(Product_ID int not null,
	CostPerKg float not null,
	primary key(Product_ID),
	foreign key (Product_ID) references product);

grant select on animal to public;

create table crop
	(Product_ID int not null,
	CostPerSeedPackage float not null,
	primary key(Product_ID),
	foreign key (Product_ID) references product);

grant select on crop to public;

-- populate tables--

insert into farmer
values(001, 'Ed Knorr');

insert into farmer
values(002, 'Raymond Ng');

insert into farmer
values(003, 'Paul Carter');

insert into farmland
values(101, 64.1);

insert into farmland
values(102, 29.8);

insert into farmland
values(103, 10.9);

insert into product
values(201, 12.2, 100, 'cow');

insert into product
values(202, 13.1, 203, 'chicken');

insert into product
values(203, 123.1, 465, 'corn');

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

