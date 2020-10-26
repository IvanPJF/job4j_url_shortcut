drop table if exists site cascade;
drop table if exists link cascade;

create table site
(
    id       serial,
    site     varchar(200) not null unique,
    login    varchar(200) not null unique,
    password varchar(200),
    primary key (id)
);

create table link
(
    id      serial,
    url     varchar(1000) not null unique,
    code    varchar(20)   not null unique,
    site_id integer,
    total   integer,
    primary key (id),
    foreign key (site_id) references site (id)
);
