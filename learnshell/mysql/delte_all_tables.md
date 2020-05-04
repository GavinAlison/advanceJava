set foreign_key_checks=0;
-- select  concat('truncate ', table_name, ';')  
-- from  information_schema.`TABLES`
-- where table_schema='any_test_hy';

truncate hibernate_sequence;
truncate mc_app_info;
truncate mc_app_project;
truncate mc_app_server;
truncate mc_auth_team;
truncate mc_auth_user;
truncate mc_content_group;
truncate mc_log;
truncate mc_organization;
truncate mc_project;
truncate mc_resource;
truncate mc_schedule_sync_user_monitor;
truncate mc_team;
truncate mc_user;
truncate mc_user_label;
truncate mc_user_label_relation;
truncate mc_user_organization;
truncate mc_user_team;

set foreign_key_checks=1;