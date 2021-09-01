CREATE TABLE roles
(
    id      serial primary key not null,
    name    varchar(10)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');


CREATE TABLE persons(
    id serial primary key not null,
    nickname varchar(25),
    password varchar(100),
    email varchar(50)
);

CREATE TABLE persons_roles(
    persons_id int references persons(id),
    roles_id int references roles(id),
    PRIMARY KEY (persons_id, roles_id)
);

CREATE TABLE messages(
    id serial primary key not null,
    message text,
    person_id int REFERENCES persons(id)
);

CREATE TABLE rooms(
                         id serial primary key not null,
                         name text
);