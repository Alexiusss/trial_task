CREATE SEQUENCE IF NOT EXISTS global_sequence START WITH 100000;

CREATE TABLE IF NOT EXISTS clients
(
    id          INT PRIMARY KEY NOT NULL,
    created_at  TIMESTAMP       NOT NULL,
    modified_at TIMESTAMP,
    version     INT             NOT NULL,
    name        VARCHAR(255)    NOT NULL
);

CREATE TABLE IF NOT EXISTS contacts
(
    id          INT PRIMARY KEY NOT NULL,
    created_at  TIMESTAMP       NOT NULL,
    modified_at TIMESTAMP,
    version     INT             NOT NULL,
    client_id   INT             NOT NULL,
    type        VARCHAR(16) CHECK ( type IN ('EMAIL', 'PHONE') ),
    value       VARCHAR(255)    NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX unique_contact_idx on contacts (value);