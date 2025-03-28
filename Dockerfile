FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/self_discovery_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9091
CMD ["java", "-jar", "app.jar"]

