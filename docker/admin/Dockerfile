FROM openjdk:8-jdk-alpine
MAINTAINER harfol 1148706823@qq.com

WORKDIR /home/admin
COPY bin .
COPY script .

ENV HARIOT_ADMIN_JAR=admin-${version:-latest-SNAPSHOT}.jar


ENV HARIOT_DATABASE_URL=jdbc:mysql://${HARIOT_DATABASE_HOST}:${HARIOT_DATABASE_POST}/${HARIOT_DATABASE}\
?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true\
&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai

EXPOSE 8080

RUN chmod a+x admin-start.sh

CMD ["/bin/sh",  "admin-start.sh"]
