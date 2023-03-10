CREATE SEQUENCE IF NOT EXISTS item_category_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS item_category (
    id INTEGER NOT NULL DEFAULT nextval('item_category_sq'),
    item_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    CONSTRAINT pk_item_category PRIMARY KEY(id),
    CONSTRAINT fk_item_category$item FOREIGN KEY (item_id) REFERENCES item(id),
    CONSTRAINT fk_item_category$category FOREIGN KEY (category_id) REFERENCES category(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_item_category$item$category ON item_category(item_id, category_id);