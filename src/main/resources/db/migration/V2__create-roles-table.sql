CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     role_name VARCHAR(45) NOT NULL,
    CONSTRAINT role_name_UNIQUE UNIQUE (role_name)
    );

INSERT INTO roles (id, role_name) VALUES
                                      (1, 'ADMIN'),
                                      (2, 'USER'),
                                      (3, 'TEACHER');
