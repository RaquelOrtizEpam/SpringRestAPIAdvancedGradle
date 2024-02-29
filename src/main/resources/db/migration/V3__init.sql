-- db/migration/V4__init.sql

create table orders
(
        id                  bigserial primary key,
        user_id             bigint,
        gift_certificate_id bigint,
        order_timestamp     timestamp,
        order_cost          bigint

);