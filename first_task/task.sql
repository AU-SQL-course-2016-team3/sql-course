-- list of sports available on this olympiad
DROP TABLE IF EXISTS Sport CASCADE;
CREATE TABLE Sport (
    id                SERIAL           PRIMARY KEY,
    name              TEXT             NOT NULL UNIQUE
);

-- list of countries which will participate in this olympiad
DROP TABLE IF EXISTS Country CASCADE;
CREATE TABLE Country (
    id                SERIAL           PRIMARY KEY,
    name              TEXT             NOT NULL
);

-- list of countries which will participate in this olympiad
DROP TABLE IF EXISTS Functionality CASCADE;
CREATE TABLE Functionality (
    id                SERIAL           PRIMARY KEY,
    function          TEXT             NOT NULL
);

-- list of constructions in olympic village
DROP TABLE IF EXISTS Construction CASCADE;
CREATE TABLE Construction (
    id                SERIAL           PRIMARY KEY,
    functionality_id  INTEGER          NOT NULL REFERENCES Functionality(id),
    address           TEXT             NOT NULL,
    name              TEXT             NULL UNIQUE
);

-- map: construction -> sports types
DROP TABLE IF EXISTS ConstructionSports CASCADE;
CREATE TABLE ConstructionSports (
    construction_id   INTEGER          NOT NULL REFERENCES Construction(id),
    sport_id          INTEGER          NOT NULL REFERENCES Sport(id),
    PRIMARY KEY (construction_id, sport_id)
);

-- list of volunteers
DROP TABLE IF EXISTS Volunteer CASCADE;
CREATE TABLE Volunteer (
    id                INTEGER          PRIMARY KEY,
    name              TEXT             NOT NULL,
    phone             TEXT             NOT NULL
);

-- list of sportsmen
DROP TABLE IF EXISTS Sportsman CASCADE;
CREATE TABLE Sportsman (
    id                INTEGER          PRIMARY KEY,
    name              TEXT             NOT NULL,
    age               INTEGER          NOT NULL CHECK (age > 0),
    sex               TEXT             NOT NULL CHECK (sex IN ('male', 'female')),
    weight            INTEGER          NOT NULL CHECK (weight > 0),
    height            INTEGER          NOT NULL CHECK (height > 0),
    country_id        INTEGER          NOT NULL REFERENCES Country(id),
    home_id           INTEGER          NOT NULL REFERENCES Construction(id),
    volunteer_id      INTEGER          NOT NULL REFERENCES Volunteer(id)
);

-- map: sportsmen -> sport
DROP TABLE IF EXISTS SportsmanSports CASCADE;
CREATE TABLE SportsmanSports (
	sportsman_id      INTEGER          NOT NULL REFERENCES Sportsman(id),
   	sport_id          INTEGER          NOT NULL REFERENCES Sport(id)
);

-- list of teams' heads
DROP TABLE IF EXISTS Head CASCADE;
CREATE TABLE Head (
    id                INTEGER          PRIMARY KEY,
    country_id        INTEGER          NOT NULL UNIQUE REFERENCES Country(id),
    name              TEXT             NOT NULL,
    phone             TEXT             NOT NULL,
    headquarters_id   INTEGER          NOT NULL REFERENCES Construction(id)
);

-- list of events
DROP TABLE IF EXISTS Event CASCADE;
CREATE TABLE Event (
    id                SERIAL           PRIMARY KEY,
    sport_id          INTEGER          NOT NULL REFERENCES Sport(id),
    event_date        DATE             NOT NULL,
    event_time        TIME             NOT NULL,
    construction_id   INTEGER          NOT NULL REFERENCES Construction(id),
    UNIQUE (event_date, event_time, construction_id)
);

-- map: sportsmen -> events
DROP TABLE IF EXISTS SportsmanEvents CASCADE;
CREATE TABLE SportsmanEvents (
    sportsman_id      INTEGER          NOT NULL REFERENCES Sportsman(id),
    event_id          INTEGER          NOT NULL REFERENCES Event(id),
    medal 			  TEXT 			   CHECK (medal IN ('gold', 'silver', 'bronze') OR medal IS NULL),
    PRIMARY KEY (sportsman_id, event_id)
);

-- list of vehicles
DROP TABLE IF EXISTS Vehicle CASCADE;
CREATE TABLE Vehicle (
    id                TEXT             PRIMARY KEY,
    capacity          INTEGER          NOT NULL CHECK (capacity > 0)
);

-- list of tasks
DROP TABLE IF EXISTS Task CASCADE;
CREATE TABLE Task (
    id                SERIAL           PRIMARY KEY,
    content           TEXT             NOT NULL,
    vehicle_id        TEXT             NOT NULL REFERENCES Vehicle(id),
    event_date        DATE             NOT NULL,
    event_time        TIME             NOT NULL
);

-- map: volunteer -> task
DROP TABLE IF EXISTS VolunteerTasks CASCADE;
CREATE TABLE VolunteerTasks (
    volunteer_id      INTEGER          NOT NULL REFERENCES Volunteer(id),
    task_id           INTEGER          NOT NULL REFERENCES Task(id),
    PRIMARY KEY (volunteer_id, task_id)
);
