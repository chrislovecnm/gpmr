
CREATE TABLE IF NOT EXISTS pet (
    id uuid,
    petId uuid,
    name text,
    description text,
    petCategoryName text,
    petCategoryId uuid,
    petSpeed float,
    PRIMARY KEY(id)
);
