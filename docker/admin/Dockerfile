FROM openjdk:8-jdk-alpine

WORKDIR /root
COPY bin .

ENV ADMIN_HOST 0.0.0.0
ENV ADMIN_PORT 8080

ENV DB_HOST hariot_db
ENV DB_PORT 3306
ENV DB_NAME hariot_iot_database
ENV DB_USER admin
ENV DB_PASSWORD 123456

EXPOSE 8080

CMD ["java", "-jar", "admin-latest-SNAPSHOT.jar"]
