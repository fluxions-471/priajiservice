FROM openjdk:17-jdk AS builder
COPY target/notification-1.0-SNAPSHOT.jar /app/notification.jar
CMD ["java", "-jar", "/app/notification.jar"]