CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     login VARCHAR(255),
                                     password VARCHAR(255),
                                     nickname VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS accounts (
                                        id BIGSERIAL PRIMARY KEY,
                                        amount BIGINT,
                                        account_type VARCHAR(255),
                                        status VARCHAR(255)
);