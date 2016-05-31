
CREATE TABLE IF NOT EXISTS raceResult (
    id uuid,
    raceResultId uuid,
    raceId uuid,
    petCategoryId uuid,
    raceParticipantId uuid,
    petName text,
    petType text,
    petColor uuid,
    petCategoryName text,
    finishPosition int,
    finishTime double,
    startTime timestamp,
    PRIMARY KEY(id)
);
