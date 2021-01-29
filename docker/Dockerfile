# sudo docker run --name mysql_test -v /home/harfol/workspace/mysql_tmp/etc/mysql:/etc/mysql -v /home/harfol/workspace/mysql_tmp/logs:/logs -v /home/harfol/workspace/mysql_tmp/var/lib/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
FROM mysql:5.7
MAINTAINER harfol 1148706823@qq.com

COPY device-admin.sql /schema/device-admin.sql

ENV MYSQL_ROOT_PASSWORD=123456
RUN echo $MYSQL_ROOT_PASSWORD
ENTRYPOINT ["/usr/sbin/mysqld"]
CMD ["/usr/bin/mysql", "--host=localhost", "--user=root", "--password=123456", "</schema/device-admin.sql"]
 
