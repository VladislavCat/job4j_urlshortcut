create table if not exists ursl (
    id serial primary key,
    url varchar unique,
    url_key varchar,
    redirect_count bytea
)