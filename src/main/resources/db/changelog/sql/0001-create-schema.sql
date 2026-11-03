--liquibase formatted sql

--changeset punch-list:0001-create-schema
CREATE SCHEMA IF NOT EXISTS punch_list;
--rollback DROP SCHEMA punch_list;
