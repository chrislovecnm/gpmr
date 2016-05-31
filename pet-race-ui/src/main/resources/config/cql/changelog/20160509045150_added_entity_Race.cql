
CREATE TABLE IF NOT EXISTS race (
    id uuid,
    raceId uuid,
    petCategoryId uuid,
    petCategoryName text,
    numOfPets int,
    length int,
    description text,
    winnerId uuid,
    startTime timestamp,
    endTime timestamp,
    baseSpeed float,
    PRIMARY KEY(id)
);
