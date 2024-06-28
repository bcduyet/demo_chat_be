FROM gradle:8.7-jdk17 AS build


# Set the working directory in the image to /app
WORKDIR /app

# Copy the gradle configuration files to our app directory
# COPY gradle gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
# COPY settings.gradle .
# Download all needed dependencies for building offline
RUN gradle wrapper
RUN chmod +x ./gradlew
# RUN ls -la ./
RUN ./gradlew 
# build --no-daemon

# Copy the rest of the project
COPY . .
RUN chmod -R 777 ./
# Build the application
RUN ./gradlew bootJar --no-daemon

# Use the official openjdk image for a lean production stage of our multi-stage build
FROM openjdk:17-jdk-slim

# Set the working directory in the image to /app
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/build/libs/*.jar xeex_chat.jar
COPY --from=build /app/.env .env
# Set the required environment variables
ENV APP_TILTE="Chat Application"
ENV APP_VERSION="1.0.0"

# ... add the rest of your environment variables here ...

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "xeex_chat.jar"]