FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/incident-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# BUG: JVM heap limited to 64MB — way too low, OOM Kill guaranteed
ENTRYPOINT ["java", "-Xmx64m", "-jar", "app.jar"]
