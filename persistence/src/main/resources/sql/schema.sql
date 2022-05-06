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