# Fetching latest version of Java
FROM openjdk:18

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/group8-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8083

# Starting the application
CMD ["java", "-jar", "group8-0.0.1-SNAPSHOT.jar"]