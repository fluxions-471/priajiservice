FROM openjdk:17-jdk AS builder
COPY target/notification-1.0-SNAPSHOT.jar /app/notification.jar
CMD ["java", "-jar", "/app/notification.jar"]

shinecountdown-0.0.1-SNAPSHOT.jar
docker run -d -p 8090:8090 --restart unless-stopped --name shinecountdown shinecountdown:latest