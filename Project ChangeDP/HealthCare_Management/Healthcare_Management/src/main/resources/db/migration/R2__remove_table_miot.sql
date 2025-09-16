-- Rollback for V2__add_table_miot.sql
DROP TABLE IF EXISTS miot.user_role CASCADE;
DROP TABLE IF EXISTS miot.role_permission_map CASCADE;
DROP TABLE IF EXISTS miot.role_permission CASCADE;
DROP TABLE IF EXISTS miot.role CASCADE;
DROP TABLE IF EXISTS miot."user" CASCADE;
DROP TABLE IF EXISTS miot.patient CASCADE;

