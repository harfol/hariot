FROM mysql:5.7
MAINTAINER harfol 1148706823@qq.com

WORKDIR /schema
COPY schema .
COPY conf/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf

ENV MYSQL_ALLOW_EMPTY_PASSWORD yes

CMD ["bash", "/schema/db-setup.sh"]  


