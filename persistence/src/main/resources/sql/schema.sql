create table certificates
(
    id          int auto_increment
        primary key,
    title       varchar(45)  not null,
    description varchar(128) not null,
    price       decimal      not null,
    duration    int          not null,
    create_date datetime     not null,
    update_date datetime     not null,
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
        foreign key (certificates_id) references certificates (id),
    constraint fk_certificates_has_tags_tags1
        foreign key (tags_id) references tags (id)
);

create index fk_certificates_has_tags_certificates_idx
    on certificates_has_tags (certificates_id);

create index fk_certificates_has_tags_tags1_idx
    on certificates_has_tags (tags_id);

create table users
(
    id       int auto_increment
        primary key,
    name     varchar(45)  not null,
    email    varchar(128) not null,
    password varchar(256) not null,
        unique (id)
);

create table orders
(
    id            int auto_increment
        primary key,
    users_id      int          not null,
    price         decimal      not null,
    purchase_date timestamp(3) not null,
        unique (id),
    constraint fk_orders_users1
        foreign key (users_id) references users (id)
);

create index fk_orders_users1_idx
    on orders (users_id);

create table orders_has_certificates
(
    orders_id       int not null,
    certificates_id int not null,
    primary key (orders_id, certificates_id),
    constraint fk_orders_has_certificates_certificates1
        foreign key (certificates_id) references certificates (id),
    constraint fk_orders_has_certificates_orders1
        foreign key (orders_id) references orders (id)
);

create index fk_orders_has_certificates_certificates1_idx
    on orders_has_certificates (certificates_id);

create index fk_orders_has_certificates_orders1_idx
    on orders_has_certificates (orders_id);

