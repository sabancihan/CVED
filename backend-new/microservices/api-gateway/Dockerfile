FROM openjdk:18-jdk-alpine
MAINTAINER sabancihan.com
COPY target/api-gateway-1.0-SNAPSHOT.jar api-gateway-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api-gateway-1.0-SNAPSHOT.jar"]