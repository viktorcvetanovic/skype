create
database skype;
create table user
(
    id       int primary key auto_increment,
    username varchar(64),
    password varchar(64)
);

create table role
(
    id   int primary key auto_increment,
    name varchar(64)
);

create table user_role(
  #dodaj ovo
);