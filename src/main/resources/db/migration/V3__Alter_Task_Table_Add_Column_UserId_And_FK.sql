ALTER TABLE task
    ADD COLUMN userid INTEGER;

ALTER TABLE task
    ADD CONSTRAINT user_fk1 FOREIGN KEY (userid) REFERENCES users (id) MATCH FULL;