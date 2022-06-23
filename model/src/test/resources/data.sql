INSERT INTO tags (name)
VALUES ('first'), ('second'), ('third'), ('fourth');

INSERT INTO gift_certificates (name, description, price, duration,
                               createDate, lastUpdateDate)
VALUES ('5element', 'tech', 100, 60, '2022-05-01 12:00:00', '2022-05-08 14:12:12'),
       ('21vek', 'for home', 70, 60, '2022-04-01 12:22:00', '2022-05-10 20:12:12'),
       ('oz', 'books', 50, 90, '2022-05-01 12:00:00', '2022-05-15 15:12:12');

INSERT INTO tags_certificates (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 3),
       (2, 3),
       (3, 1);
