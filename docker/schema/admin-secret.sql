use mysql;
select host, user from user;
create user docker identified by '123456';
grant all on device_admin.* to docker@'%' identified by '123456' with grant option;
flush privileges;
