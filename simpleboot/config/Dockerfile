FROM openjdk:8
MAINTAINER alison

ADD ./target/config.jar /app/
CMD ["java", "-jar", "-Duser.timezone=GMT+08", "/app/config.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:7998/actuator/health || exit 1

EXPOSE 7998