CREATE TABLE  IF NOT EXISTS admin_account
(
    id           VARCHAR(36) NOT NULL PRIMARY KEY,
    username     VARCHAR(255) NOT NULL unique,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(36),
    created_by   VARCHAR(255) NOT NULL,
    created_time timestamp    NOT NULL,
    updated_by   VARCHAR(255),
    updated_time timestamp
);

