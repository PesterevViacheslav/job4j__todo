CREATE SEQUENCE IF NOT EXISTS priorities_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS priorities (
    id INTEGER NOT NULL DEFAULT nextval('priorities_sq'),
    name TEXT NOT NULL,
    position INTEGER NOT NULL,
    CONSTRAINT pk_priorities PRIMARY KEY(id)
);