DROP TABLE IF EXISTS COMMUNICATION;

CREATE TABLE COMMUNICATION (
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL UNIQUE,
    body              BYTEA,
    delivery_settings BYTEA,
    size              BIGINT,
    type              VARCHAR(100),
    is_processed      BOOLEAN DEFAULT false,
    is_sent           BOOLEAN DEFAULT false
)