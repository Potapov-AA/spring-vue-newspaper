-- Удаление таблиц перед созданием --
DROP TABLE IF EXISTS user_theme_like_dislike;
DROP TABLE IF EXISTS theme_article;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS theme;
DROP TABLE IF EXISTS article;

DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;



-- СОЗДАНИЕ И ЗАПОЛНЕНИЕ ТАБЛИЦ User; Roles; User_roles --
CREATE TABLE users (
  id                    serial,
  firstname             varchar(100) not null,
  lastname				varchar(100) not null,
  password              varchar(100) not null,
  email                 varchar(100) unique,
  primary key (id)
);

CREATE TABLE roles (
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

INSERT INTO roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (firstname, lastname, password, email)
values
('eva', 'pervovich', '$2a$10$Lrkc8fqeuMgBNcu9Ng0BFOLnu6g4QCxhEPveH6TAU2pVA5e2kw9aG', 'user@gmail.com'),
('jester', 'shutov', '$2a$10$Lrkc8fqeuMgBNcu9Ng0BFOLnu6g4QCxhEPveH6TAU2pVA5e2kw9aG', 'jester@gmail.com'),
('adam', 'pervovich', '$2a$10$Lrkc8fqeuMgBNcu9Ng0BFOLnu6g4QCxhEPveH6TAU2pVA5e2kw9aG', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
values
(1, 1),
(2, 1),
(3, 2);



-- СОЗДАНИЕ И ЗАПОЛНЕНИЕ ТАБЛИЦ article; like; theme; theme_article, user_theme_like, user_theme_dislike --
CREATE TABLE article (
	id		serial not null,
	title 	varchar(100) not null,
	text 	text not null,
	image 	text,
	date 	timestamp with time zone not null,
	primary key (id) 
);

INSERT INTO article (title, text, image, date)
values
('Человек-паук снова терраризирует город!', 
 'Дорогие читатели! \n Сегодня я хотел бы рассказать вам о человеке-пауке, или как его именуют в преступном мире - Питере Паркере. Возможно, вы думаете, что этот молодой человек с красно-синим костюмом и паучьими способностями заслуживает наше восхищение и похвалу. Однако, я хочу вам представить иной взгляд на этого так называемого "героя". \n Что же такого особенного в этом Питере Паркере? Паучьи силы? Лазание по стенам? Подумайте об этом чуть глубже. Неужели лучше, чтобы у нас в городе появилось еще одно создание, способное проникать в частную собственность, просачиваться повсюду и шпионить за нами? Этот парень, вместо того чтобы использовать свои умения для блага общества, предпочитает ходить по строптивому пути и высмеивать законы и порядок нашего города. \n Часто можно слышать о его конфликте с правоохранительными органами. Он открыто игнорирует указания полиции и нарушает порядок, считая, что он самый умный и что ему не подчиняются правила. Это жалкий пример для нашей молодежи, которая начинает верить, что можно делать что угодно, если только у тебя есть "суперспособности". \n А что насчет его способностей? Клубок паутины может быть интересным гаджетом, но давайте посмотрим правде в глаза - это всего лишь очередное оружие, которое он использует для своих эгоистических целей. Он своеобразный юстициарий, который без какого-либо суда и следствия навязывает свою "правду". Кто дал ему право на такие действия? \n И давайте не забудем о его личной жизни. Питер Паркер - просто самозванец, который испытывает внутренние конфликты между своими обязанностями в качестве "героя" и своей личной жизнью. Не заслуживает ли наше сострадание его печальная судьба? Возможно, если бы он сконцентрировался на своей учебе или карьере, а не на игре в супергероя, его жизнь была бы иначе. Так что я предлагаю вам задуматься о реальной значимости этого так называемого "человека-паука". Вместо того, чтобы слепо верить в его образ героя, давайте посмотрим на факты и подумаем о последствиях его действий. Ведь, как говорится, дорога в ад всегда вымощена хорошими намерениями.', 
 'data:image/jpeg;base64,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', 
 NOW());

CREATE TABLE likes (
	article_id 	serial not null,
	user_id serial not null,
	primary key (article_id, user_id),
	foreign key (user_id) references users (id),
  	foreign key (article_id) references article (id)
);

INSERT INTO likes (article_id, user_id)
values
(1, 1),
(1, 2);

CREATE TABLE comment (
	id 			serial not null,
	text 		varchar(1000) not null,
	date 		timestamp with time zone not null,
	user_id 	serial not null,
	article_id 	serial not null,
	primary key (id),
	foreign key (user_id) references users (id),
  	foreign key (article_id) references article (id)
);

INSERT INTO comment (text, date, user_id, article_id)
values
('Какой ужас!', NOW(), 1, 1),
('Не правда он герой!!!', NOW(), 2, 1);

CREATE TABLE theme (
	id		serial not null,
	name	varchar(100) not null,
	primary key (id)
);

INSERT INTO theme (name)
values
('Человек-Паук'),
('Город');

CREATE TABLE theme_article (
	theme_id		serial not null,
	article_id		serial not null,
	primary key (theme_id, article_id),
	foreign key (theme_id) references theme (id),
  	foreign key (article_id) references article (id)
);

INSERT INTO theme_article (theme_id, article_id)
values
(1, 1),
(2, 1);

CREATE TABLE user_theme_like_dislike (
	theme_id 	serial not null,
	user_id 	serial not null,
	status		serial not null,
	primary key (theme_id, user_id),
	foreign key (user_id) references users (id),
  	foreign key (theme_id) references theme (id)
);

INSERT INTO user_theme_like_dislike (theme_id, user_id, status)
values
(1, 1, 1),
(2, 1, 1),
(2, 2, -1);
