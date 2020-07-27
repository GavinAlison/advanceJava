FROM openjdk:8
MAINTAINER alison

ADD ./target/registry.jar /app/
CMD ["java", "-jar", "-Duser.timezone=GMT+08", "/app/registry.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:7999/actuator/health || exit 1

EXPOSE 7999