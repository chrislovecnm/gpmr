
CREATE TABLE IF NOT EXISTS raceParticipant (
    id uuid,
    raceParticipantId uuid,
    petId uuid,
    raceId uuid,
    petName text,
    petColor uuid,
    petCategoryName text,
    petCategoryId uuid,
    startTime timestamp,
    finishTime double,
    finishPosition int,
    finished boolean,
    PRIMARY KEY(id)
);
