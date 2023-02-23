INSERT INTO priorities (name, position) VALUES ('urgently', 1), ('normal', 2);
UPDATE item SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently') WHERE priority_id IS NULL;