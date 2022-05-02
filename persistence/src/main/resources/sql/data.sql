INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_1', 'Description_1', 111, 11, '2022-03-21 20:06:48', '2022-03-21 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_2', 'Description_2', 222, 22, '2022-03-22 20:06:48', '2022-03-22 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_3', 'Description_3', 333, 33, '2022-03-23 20:06:48', '2022-03-23 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_4', 'Description_4', 444, 44, '2022-03-23 20:06:48', '2022-03-23 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_5', 'Description_5', 555, 55, '2022-03-23 20:06:48', '2022-03-23 20:06:50');

INSERT INTO tags (title) VALUES ('tag_1');
INSERT INTO tags (title) VALUES ('tag_2');
INSERT INTO tags (title) VALUES ('tag_3');
INSERT INTO tags (title) VALUES ('tag_4');
INSERT INTO tags (title) VALUES ('tag_5');

INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 1);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 2);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 3);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (2, 1);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (2, 2);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (3, 1);