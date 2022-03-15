CREATE TABLE myTable (
    id BIGINT identity primary key,
    name varchar(63),
    someUuid uniqueidentifier not null
);

CREATE TABLE children (
    id BIGINT identity primary key,
    parentId BIGINT,
    name varchar(63),
    CONSTRAINT fk_children_myTable FOREIGN KEY (parentId) REFERENCES myTable
);