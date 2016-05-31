
CREATE TABLE IF NOT EXISTS raceNormal (
    id uuid,
    raceNormalId uuid,
    raceId uuid,
    petCategoryId uuid,
    petCategoryName text,
    currentTime timestamp,
    normalLoc float,
    normalScale float,
    normalSize int,
    PRIMARY KEY(id)
);
