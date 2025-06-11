CREATE TABLE users
(
    id          uuid PRIMARY KEY,
    firebase_id VARCHAR(36) UNIQUE,
    email       VARCHAR(150) UNIQUE,
    roles       VARCHAR(50)[] NOT NULL
);
