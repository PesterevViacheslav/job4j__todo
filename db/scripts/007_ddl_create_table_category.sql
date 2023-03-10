CREATE SEQUENCE IF NOT EXISTS category_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS category (
     id INTEGER NOT NULL DEFAULT nextval('category_sq'),
     name VARCHAR(200) NOT NULL,
     CONSTRAINT pk_category PRIMARY KEY(id)
);

INSERT INTO category VALUES (default, 'Work'), (default, 'Home'), (default, 'Other');