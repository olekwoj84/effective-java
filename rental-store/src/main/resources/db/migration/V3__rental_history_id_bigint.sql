ALTER TABLE rental_history ALTER COLUMN id type BIGINT;
DROP SEQUENCE rental_history_id_seq CASCADE;
