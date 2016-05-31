
CREATE TABLE IF NOT EXISTS raceData (
    id uuid,
    raceDataId uuid,
    petId uuid,
    raceId uuid,
    petName text,
    petCategoryName text,
    petCategoryId uuid,
    interval int,
    runnerPosition int,
    runnerDistance double,
    startTime timestamp,
    finished boolean,
    runnerPreviousDistance double,
    PRIMARY KEY(id)
);
