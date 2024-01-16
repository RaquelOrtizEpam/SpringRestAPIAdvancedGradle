INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration_in_days)
VALUES ('Gift1', 'description', 9.99, '2023-07-26 21:08:43.000', '2023-07-26 21:08:43.000', 1),
       ('Gift2', 'description', 9.99, '2023-07-26 21:08:43.000', '2023-07-26 21:08:43.000', 1),
       ('Gift3', 'description', 9.99, '2023-07-26 21:08:43.000', '2023-07-26 21:08:43.000', 1),
       ('Gift4', 'description', 9.99, '2023-07-26 21:08:43.000', '2023-07-26 21:08:43.000', 1),
       ('Gift5', 'description', 9.99, '2023-07-26 21:08:43.000', '2023-07-26 21:08:43.000', 1);

INSERT INTO tag (name)
VALUES ('tag1'),
       ('tag2'),
       ('tag3'),
       ('tag4'),
       ('tag5'),
       ('tag6');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 5),
       (3, 3),
       (3, 4),
       (4, 5),
       (5, 1),
       (5, 6);

INSERT INTO users (name, email)
VALUES ('user1', 'user1gmail'),
       ('user2', 'user2@gmail.com'),
       ('user3', 'user3@gmail.com'),
       ('user4', 'user4@gmail.com'),
       ('user5', 'user5@gmail.com');