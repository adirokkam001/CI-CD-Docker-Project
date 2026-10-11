
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/cicd-docker-project-1.0.0.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]

