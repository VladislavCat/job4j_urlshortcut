create table if not exists sites (
    id_sites serial primary key,
    name varchar unique,
    login varchar,
    password varchar
)