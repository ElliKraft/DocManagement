//DROP TABLE IF EXISTS DOCUMENTS;
//DROP TABLE IF EXISTS DOC_CATEGORIES;
//DROP TABLE IF EXISTS USER_ROLES;
//DROP TABLE IF EXISTS USERS;
/*
create table users
(
    id BIGINT auto_increment,
    constraint User_pk primary key (id),
    name TEXT not null,
    surname TEXT not null,
    company TEXT not null,
    email TEXT not null,
    password TEXT not null,
    deleted BOOLEAN default false,
    creation_date DATE,
    last_modified_date DATE
);
create unique index User_Id_uindex on users (id);
*/
INSERT INTO USERS (name, surname, company, email, password, deleted)
VALUES ('Frida', 'Kahlo', 'Artist&Co', 'kahlo@artist.br', 'burum', false),
       ('Claude', 'Monet', 'MalerCompany', 'monet@maler.fr', 'burum', false),
       ('William', 'Turner', 'PaintersCompany', 'turner@painter.gb', 'burum', false)
