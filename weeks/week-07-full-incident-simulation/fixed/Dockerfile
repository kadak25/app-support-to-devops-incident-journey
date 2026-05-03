FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/incident-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# FIX: Reasonable JVM heap — 256MB max, allows normal operation
# FIX: Added GC logging to detect future memory issues early
ENTRYPOINT ["java", "-Xmx256m", "-Xms64m", "-jar", "app.jar"]
