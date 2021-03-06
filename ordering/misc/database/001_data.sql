CREATE DATABASE ordering;

CREATE USER ordering WITH PASSWORD 'ordering';

ALTER USER ordering WITH SUPERUSER;

GRANT ALL PRIVILEGES ON DATABASE ordering TO ordering;
GRANT ALL PRIVILEGES ON SCHEMA public TO ordering;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ordering;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ordering;

SET TIME ZONE 'America/Montreal';
SET TIMEZONE TO 'America/Montreal';
ALTER DATABASE ordering SET timezone TO 'America/Montreal';

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
