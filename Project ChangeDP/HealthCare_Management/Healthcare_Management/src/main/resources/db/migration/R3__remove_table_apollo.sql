-- Rollback for V3__add_table_apollo.sql
DROP TABLE IF EXISTS apollo.user_role CASCADE;
DROP TABLE IF EXISTS apollo.role_permission_map CASCADE;
DROP TABLE IF EXISTS apollo.role_permission CASCADE;
DROP TABLE IF EXISTS apollo.role CASCADE;
DROP TABLE IF EXISTS apollo."user" CASCADE;
DROP TABLE IF EXISTS apollo.patient CASCADE;

