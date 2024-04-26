FROM openjdk:17-jdk AS builder
COPY target/security-1.0-SNAPSHOT.jar /app/security.jar
CMD ["java", "-jar", "/app/security.jar"]