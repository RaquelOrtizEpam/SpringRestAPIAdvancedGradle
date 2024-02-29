ALTER TABLE users
DROP COLUMN role;
DROP TABLE roles;

create table roles(
    id   int primary key,
    name varchar(20)
);
