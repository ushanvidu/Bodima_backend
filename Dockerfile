# Use Amazon Corretto (JDK 17/21 recommended for Spring Boot)
FROM amazoncorretto:24-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/*.jar app.jar

# Expose your Spring Boot app port
EXPOSE 8090

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]