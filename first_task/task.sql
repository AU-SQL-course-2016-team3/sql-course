-- list of sports available on this olympiad
CREATE TABLE Sport (
    id                SERIAL           PRIMARY KEY,
    name              TEXT             NOT NULL UNIQUE CHECK (name SIMILAR TO '([A-Z][a-z]*[:space:])*[A-Z][a-z]*')
);

-- list of countries which will participate in this olympiad
CREATE TABLE Country (
    id                SERIAL           PRIMARY KEY,
    name              TEXT             NOT NULL UNIQUE CHECK (name SIMILAR TO '([A-Z][a-z]*[:space:])*[A-Z][a-z]*')
);

-- list of constructions in olympic village
CREATE TABLE Construction (
    id                SERIAL           PRIMARY KEY,
    functional_type   TEXT             NOT NULL,
    address           TEXT             NOT NULL,
    name              TEXT             NULL UNIQUE
);

-- map: construction -> sports types
CREATE TABLE ConstructionSports (
    construction_id   INTEGER          NOT NULL REFERENCES Construction(id),
    sport_id          INTEGER          NOT NULL REFERENCES Sport(id),
    PRIMARY KEY (construction_id, sport_id)
);

-- list of volunteers
CREATE TABLE Volunteer (
    id                INTEGER          PRIMARY KEY,
    name              TEXT             NOT NULL CHECK (name SIMILAR to '([A-Z][a-z]*[:space:])*[A-Z][a-z]*'),
    phone             TEXT             NOT NULL UNIQUE CHECK (phone SIMILAR TO '\+[0-9]*')
);

-- list of sportsmen
CREATE TABLE Sportsman (
    id                INTEGER          PRIMARY KEY,
    name              TEXT             NOT NULL CHECK (name SIMILAR TO '([A-Z][a-z]*[:space:])*[A-Z][a-z]*'),
    age               INTEGER          NOT NULL CHECK (age > 0),
    sex               TEXT             NOT NULL CHECK (sex IN ('male', 'female')),
    weight            INTEGER          NOT NULL CHECK (weight > 0),
    height            INTEGER          NOT NULL CHECK (height > 0),
    country_id        INTEGER          NOT NULL REFERENCES Country(id),
    home_id           INTEGER          NOT NULL REFERENCES Construction(id),
    volunteer_id      INTEGER          NOT NULL REFERENCES Volunteer(id)
);

-- list of teams' heads
CREATE TABLE Head (
    id                INTEGER          PRIMARY KEY,
    country_id        INTEGER          NOT NULL UNIQUE REFERENCES Country(id),
    name              TEXT             NOT NULL CHECK (name SIMILAR TO '([A-Z][a-z]*[:space:])*[A-Z][a-z]*'),
    phone             TEXT             NOT NULL UNIQUE CHECK (phone SIMILAR TO '\+[0-9]*'),
    headquarters_id   INTEGER          NOT NULL UNIQUE REFERENCES Construction(id)
);

-- list of events
CREATE TABLE Event (
    id                SERIAL           PRIMARY KEY,
    sport_id          INTEGER          NOT NULL REFERENCES Sport(id),
    event_date        DATE             NOT NULL,
    event_time        TIME             NOT NULL,
    construction_id   INTEGER          NOT NULL REFERENCES Construction(id),
    UNIQUE (event_date, event_time, construction_id)
);

-- map: sportsmen -> events
CREATE TABLE SportsmanEvents (
    sportsman_id      INTEGER          NOT NULL REFERENCES Sportsman(id),
    event_id          INTEGER          NOT NULL REFERENCES Event(id),
    PRIMARY KEY (sportsman_id, event_id)
);

-- map: sportsmen -> their medals
CREATE TABLE Medal (
    sportsman_id      INTEGER          NOT NULL REFERENCES Sportsman(id),
    event_id          INTEGER          NOT NULL REFERENCES Event(id),
    medal             TEXT             NOT NULL CHECK (medal IN ('gold', 'silver', 'bronze')),
    PRIMARY KEY (sportsman_id, event_id)
--    ,UNIQUE (event_id, medal)    there can be group competitions
);

-- list of vehicles
CREATE TABLE Vehicle (
    id                INTEGER          PRIMARY KEY,
    capacity          INTEGER          NOT NULL CHECK (capacity > 0)
);

-- list of tasks
CREATE TABLE Task (
    id                SERIAL           PRIMARY KEY,
    content           TEXT             NOT NULL,
    vehicle_id        INTEGER          NULL REFERENCES Vehicle(id)
);

-- map: volunteer -> task
CREATE TABLE VolunteerTasks (
    volunteer_id      INTEGER          NOT NULL REFERENCES Volunteer(id),
    task_id           INTEGER          NOT NULL REFERENCES Task(id),
    PRIMARY KEY (volunteer_id, task_id)
);
