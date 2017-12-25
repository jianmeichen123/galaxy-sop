CREATE TABLE sop_file_history LIKE sop_file;
ALTER TABLE sop_file_history ADD COLUMN file_id BIGINT(20) NOT NULL;