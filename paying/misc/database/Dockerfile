FROM postgres:12.1-alpine

COPY 001_data.sql /docker-entrypoint-initdb.d/001_data.sql

ENV PGDATA=/data

EXPOSE 5432
