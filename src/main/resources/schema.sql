create table if not exists transactions (
    id              uuid            default random_uuid() primary key,
    receiving_user  varchar(255)    not null,
    amount          decimal(15, 4)  not null,
    `timestamp`     timestamp       not null,
    reference       varchar(255),
    slogan          varchar(255)    not null
);
