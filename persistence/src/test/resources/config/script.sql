create table certificates
(
    id          int auto_increment
        primary key,
    title       varchar(45)  not null,
    description varchar(128) not null,
    price       decimal          not null,
    duration    int          not null,
    create_date datetime     not null,
    update_date datetime     not null,
    constraint id_UNIQUE
        unique (id)
);

create table tags
(
    id    int auto_increment
        primary key,
    title varchar(45) not null
);

create table certificates_has_tags
(
    certificates_id int not null,
    tags_id         int not null,
    primary key (certificates_id, tags_id),
    constraint fk_certificates_has_tags_certificates
        foreign key (certificates_id) references certificates (id)
            on delete cascade,
    constraint fk_certificates_has_tags_tags1
        foreign key (tags_id) references tags (id)
);

create index fk_certificates_has_tags_certificates_idx
    on certificates_has_tags (certificates_id);

create index fk_certificates_has_tags_tags1_idx
    on certificates_has_tags (tags_id);


INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_1', 'Description_1', 111, 11, '2022-03-21 20:06:48', '2022-03-21 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_2', 'Description_2', 222, 22, '2022-03-22 20:06:48', '2022-03-22 20:06:50');
INSERT INTO certificates (title, description, price, duration, create_date, update_date)
VALUES ('Title_3', 'Description_3', 333, 33, '2022-03-23 20:06:48', '2022-03-23 20:06:50');

INSERT INTO tags (title) VALUES ('tag_1');
INSERT INTO tags (title) VALUES ('tag_2');
INSERT INTO tags (title) VALUES ('tag_3');
INSERT INTO tags (title) VALUES ('tag_4');

INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 1);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 2);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (1, 3);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (2, 1);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (2, 2);
INSERT INTO certificates_has_tags(certificates_id, tags_id) VALUES (3, 1);