-- db/migration/V6__init.sql

ALTER TABLE users
ADD COLUMN pass VARCHAR(255);