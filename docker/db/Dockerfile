FROM mysql:5.7
MAINTAINER harfol 1148706823@qq.com

WORKDIR /home/mysql

COPY conf/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf
COPY conf/setup.sh /docker-entrypoint-initdb.d/
COPY conf/tables_info.sql .



ENV MYSQL_ALLOW_EMPTY_PASSWORD=yes


