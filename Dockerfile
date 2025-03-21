# Stage 1: Dependency cache layer
FROM docker.io/gradle:8.12.1-jdk23 AS dependencies
WORKDIR /app

# Copy only dependency-related files
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .
COPY libs.versions.toml .
COPY spotless.xml .

# Pre-download dependencies for caching - this layer will be reused if dependencies don't change
RUN ./gradlew --no-daemon downloadDependencies || \
    ./gradlew --no-daemon dependencies

# Stage 2: Build layer
FROM dependencies AS builder
WORKDIR /app

# Copy source code
COPY src src

# Build the application (skipping tests to speed up build)
RUN ./gradlew --no-daemon clean build -x test -x spotlessCheck

# Stage 3: Runtime layer - using a slim JRE
FROM docker.io/amazoncorretto:23-alpine AS runtime
WORKDIR /app

# Add JVM optimization flags
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -Dspring.profiles.active=production"

# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/asynchronous-http-server-0.0.1-SNAPSHOT.jar app.jar

# Set the entrypoint with optimized JVM settings
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
