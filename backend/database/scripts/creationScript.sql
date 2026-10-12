-- 1. START AT THE MASTER SYSTEM DATABASE
\c postgres

-- Forcefully terminate any active connections to the target database
SELECT pg_terminate_backend(pid) 
FROM pg_stat_activity 
WHERE datname = 'servicemanager' AND pid <> pg_backend_pid();

-- Drop the database container along with its 11 locked objects instantly
DROP DATABASE IF EXISTS servicemanager WITH (FORCE);

-- Now that the database container is gone, clean up the global user role
REASSIGN OWNED BY serviceManager TO postgres;
DROP OWNED BY serviceManager;
DROP USER IF EXISTS serviceManager;

-- 2. CREATE AND REBUILD EVERYTHING FRESH
CREATE DATABASE servicemanager;

-- Switch to the fresh target database
\c servicemanager

-- Now recreate the application user
CREATE USER serviceManager WITH PASSWORD 'P@55w0rd';

ALTER SCHEMA public OWNER TO serviceManager;

-- Assign Default Privileges
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
GRANT ALL PRIVILEGES ON TABLES TO serviceManager;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
GRANT ALL PRIVILEGES ON SEQUENCES TO serviceManager;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
GRANT ALL PRIVILEGES ON FUNCTIONS TO serviceManager;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
GRANT ALL PRIVILEGES ON TYPES TO serviceManager;

-- Create Database Versioning Layout
CREATE TABLE dbVersion (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    versionNumber INT NOT NULL
);

CREATE FUNCTION get_db_version()
RETURNS INT
AS $$
DECLARE
    version INT;
    
BEGIN
    SELECT versionNumber INTO version FROM dbVersion ORDER BY id DESC LIMIT 1;
    RETURN version;
END;
$$ LANGUAGE plpgsql;

INSERT INTO dbVersion(versionNumber) VALUES (0);

-- ###################################### MIGRATION SCRIPT 1 ######################################

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