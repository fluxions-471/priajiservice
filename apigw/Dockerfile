FROM openjdk:17-jdk AS builder
COPY target/apigw-1.0-SNAPSHOT.jar /app/apigw.jar
CMD ["java", "-jar", "/app/apigw.jar"]