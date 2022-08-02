

CREATE TABLE employee (
    id int NOT NULL primary key AUTO_INCREMENT,
    last_name varchar(255),
    first_name varchar(255),
    email varchar(255)
);

CREATE TABLE users (
    user_id int NOT NULL primary key AUTO_INCREMENT,
    username varchar(255),
    password varchar(255)
);
CREATE TABLE roles (
    role_id int NOT NULL primary key AUTO_INCREMENT,
    name varchar(255)
);

CREATE TABLE users_roles (
    user_id int,
    role_id int,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

insert into employee (id,email,first_name,last_name) values (1,'mverma@gmail.com','manisha','verma');
insert into users (user_id,username,password) values(1,'manisha','$2a$10$B8M7ijj5t5uXbdv/rzafquPaoSgERjqAItNTCepXg28sj0r7i.Hli');

insert into roles (role_id,name) values(1,'ADMIN');
insert into users_roles (user_id,role_id) values(1,1);

/*
SELECT * FROM  ROLES ;
SELECT * FROM  EMPLOYEE ;
SELECT * FROM USERS ;
SELECT * FROM USERS_ROLES ;
insert into employee (id,email,first_name,last_name) values (1,'mverma@gmail.com','manisha','verma');
insert into employee (id,email,first_name,last_name) values (2,'kshitij@gmail.com','kshitij','verma');

insert into roles (role_id,name) values(1,'ADMIN');
insert into roles (role_id,name) values(2,'USER');*/

