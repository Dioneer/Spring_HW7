

create table if not exists security(
id bigserial primary key,
email varchar(125) unique,
role varchar(50) not null,
image varchar(254),
password varchar(128) default '{noop}password'
);
--drop table security;

insert into security (email, role , image) values ('ivanov@gmail.com','admin','51-511364_item-skunk-skunk-png.jpg'),
('petrov@gmail.com','user','light_bulb_light_dark_226180_1200x1600.jpg'),
('sidorov@gmail.com','admin','Screenshot_1.jpg'),('kluev@gmail.com','user','Screenshot_7.jpg'),
('pronin@gmail.com','user', 'var_2.jpg');