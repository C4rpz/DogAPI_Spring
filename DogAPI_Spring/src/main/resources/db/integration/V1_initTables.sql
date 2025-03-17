CREATE TABLE roles (
                       id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255)
);

CREATE TABLE users (
                       id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username         VARCHAR(255) NOT NULL UNIQUE,
                       password         VARCHAR(255) NOT NULL,
                       enabled          BIT(1)       NOT NULL,
                       token_expiration DATETIME,
                       claim_token      VARCHAR(255)
);

CREATE TABLE users_roles (
                             roles_id BIGINT NOT NULL,
                             users_id BIGINT NOT NULL,
                             PRIMARY KEY (roles_id, users_id),
                             FOREIGN KEY (roles_id) REFERENCES roles (id),
                             FOREIGN KEY (users_id) REFERENCES users (id)
);

CREATE TABLE dogs (
                      id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                      breed   VARCHAR(255) NOT NULL,
                      img_url VARCHAR(512)
);
