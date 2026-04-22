# FIX: Pinned Java 17 slim image — compatible with Spring Boot 3.x
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# JAR is produced by Maven build in pipeline
COPY target/incident-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
