create table tag
(
    id   bigserial primary key,
    name varchar(255) UNIQUE
);

create table gift_certificate
(
    id               bigserial primary key,
    name             varchar(255),
    description      varchar(255),
    price            float,
    create_date      timestamp without time zone,
    last_update_date timestamp without time zone,
    duration_in_days integer
);

create table gift_certificate_tag
(
    gift_certificate_id bigint references gift_certificate (id),
    tag_id              bigint references tag (id),
    primary key (gift_certificate_id, tag_id)
);

create table users
(
    id               bigserial primary key,
    name             varchar(255),
    email      varchar(255)
);