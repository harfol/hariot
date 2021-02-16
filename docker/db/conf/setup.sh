#!/bin/bash

mysql -uroot -e "CREATE DATABASE  IF NOT EXISTS $HARIOT_DATABASE;"

mysql -uroot $HARIOT_DATABASE <tables_info.sql

mysql -uroot mysql >/dev/null <<EOF
select host, user from user;
create user $HARIOT_DATABASE_USER identified by '$HARIOT_DATABASE_PASSWORD';
grant all on $HARIOT_DATABASE.* to $HARIOT_DATABASE_USER@'%' identified by '$HARIOT_DATABASE_PASSWORD' with grant option;
flush privileges;
EOF
