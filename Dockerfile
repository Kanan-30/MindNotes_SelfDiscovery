FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/self_discovery_service.jar app.jar
EXPOSE 9091
CMD ["java", "-jar", "app.jar"]

