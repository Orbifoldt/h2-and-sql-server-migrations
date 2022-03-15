CREATE TABLE myTable (
    id BIGINT identity primary key,
    name varchar(63),
    someUuid uniqueidentifier not null
);