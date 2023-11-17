create table users (
  id                    serial,
  firstname             varchar(100) not null,
  lastname				varchar(100) not null,
  password              varchar(100) not null,
  email                 varchar(100) unique,
  primary key (id)
);

create table roles (
  id                    serial,
  name                  varchar(100) not null,
  primary key (id)
);

CREATE TABLE users_roles (
  user_id               serial not null,
  role_id               serial not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN');

insert into users (firstname, lastname, password, email)
values
('user for test', 'lastname', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
('admin for test', 'lastname', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

CREATE TABLE article (
	id		serial not null,
	title 	varchar(100) not null,
	text 	text not null,
	image 	bytea,
	date 	date not null,
	primary key (id)
);

insert into article (title, text, image, date)
values
('test title 1', 'text text text', null, '2023-11-17'),
('test title2222', 'text text text', null, '2023-11-17');

CREATE TABLE likes (
	article_id 	serial not null,
	user_id serial not null,
	primary key (article_id, user_id),
	foreign key (user_id) references users (id),
  	foreign key (article_id) references article (id)
);

insert into likes (article_id, user_id)
values
(1, 1),
(1, 2),
(2, 1),
(2, 2);

CREATE TABLE comment (
	id 			serial not null,
	text 		varchar(1000) not null,
	date 		date not null,
	user_id 	serial not null,
	article_id 	serial not null,
	primary key (id),
	foreign key (user_id) references users (id),
  	foreign key (article_id) references article (id)
);

insert into comment (text, date, user_id, article_id)
values
('comment 1', '2023-11-17', 1, 1),
('comment 33333', '2023-11-17', 1, 1),
('comment 2', '2023-11-17', 1, 2);

CREATE TABLE theme (
	id		serial not null,
	name	varchar(100) not null,
	primary key (id)
);

insert into theme (name)
values
('TEST1'),
('TEST2');

CREATE TABLE themes_articles (
	theme_id		serial not null,
	article_id		serial not null,
	primary key (theme_id, article_id),
	foreign key (theme_id) references theme (id),
  	foreign key (article_id) references article (id)
);

insert into themes_articles (theme_id, article_id)
values
(1, 1),
(2, 1),
(1, 2);