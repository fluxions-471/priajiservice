FROM openjdk:17-jdk AS builder
COPY target/fraud-1.0-SNAPSHOT.jar /app/fraud.jar
CMD ["java", "-jar", "/app/fraud.jar"]