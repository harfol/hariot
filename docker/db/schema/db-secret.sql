use mysql;
select host, user from user;
create user admin identified by '123456';
grant all on hariot_iot_database.* to admin@'%' identified by '123456' with grant option;
flush privileges;
