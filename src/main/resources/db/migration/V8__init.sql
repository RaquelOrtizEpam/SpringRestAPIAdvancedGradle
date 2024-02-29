-- db/migration/V8__init.sql


create table roles
(
    id               bigserial primary key,
    name             varchar(255)
);