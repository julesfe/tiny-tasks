CREATE TABLE "users" (
    id VARCHAR(36) CONSTRAINT user_id_pkey PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR (128) NOT NULL
);
