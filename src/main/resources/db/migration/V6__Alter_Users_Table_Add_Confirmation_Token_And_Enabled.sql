ALTER TABLE users
    ADD COLUMN confirmation_token VARCHAR(36) UNIQUE;

ALTER TABLE users
    ADD COLUMN enabled BOOLEAN;
