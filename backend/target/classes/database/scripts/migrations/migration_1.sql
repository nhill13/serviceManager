BEGIN;

DROP FUNCTION IF EXISTS add_user(username VARCHAR(50), password VARCHAR(255), email VARCHAR(100));
DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE FUNCTION add_user(username VARCHAR(50), password VARCHAR(255), email VARCHAR(100))
RETURNS INTEGER
AS $$
DECLARE
    user_id INTEGER;
BEGIN
    INSERT INTO app_user (username, password, email) VALUES (username, password, email) RETURNING id INTO user_id;
    RETURN user_id;
END;
$$ LANGUAGE plpgsql;

INSERT INTO dbVersion(versionNumber) VALUES (1);

END;