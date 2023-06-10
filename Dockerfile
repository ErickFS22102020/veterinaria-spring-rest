# Use a smaller base image
FROM openjdk:11-jre-slim

# Copy the JAR file
COPY "./src/main/resources/veterinaria-0.0.1-SNAPSHOT.jar" "app.jar"

# Expose port 8081
EXPOSE 8081

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
