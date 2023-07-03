# Use a base image with Java 8
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application WAR file to the container
COPY ./build/libs/backend-0.0.1-SNAPSHOT.war /app/backend.war

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "backend.war"]
