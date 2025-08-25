# Amazon Corretto has good M1 support
FROM arm64v8/amazoncorretto:17-alpine

WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]