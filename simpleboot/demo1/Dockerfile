FROM openjdk:8
MAINTAINER alison

ADD ./target/demo1-service.jar /app/
CMD ["java", "-jar", "-Duser.timezone=GMT+08", "/app/demo1-service.jar"]

EXPOSE 8011