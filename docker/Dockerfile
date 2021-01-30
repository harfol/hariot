# sudo docker run --name mysql_test -v /home/harfol/workspace/mysql_tmp/etc/mysql:/etc/mysql -v /home/harfol/workspace/mysql_tmp/logs:/logs -v /home/harfol/workspace/mysql_tmp/var/lib/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
FROM mysql:5.7
MAINTAINER harfol 1148706823@qq.com

WORKDIR /sql
COPY schema .
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes

CMD ["bash", "/sql/admin-setup.sh"]  


