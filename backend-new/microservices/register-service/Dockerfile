FROM openjdk:18-jdk-alpine
MAINTAINER sabancihan.com
COPY target/register-service-1.0-SNAPSHOT.jar register-service-1.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/register-service-1.0-SNAPSHOT.jar"]