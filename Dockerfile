# ---- Stage 1: Build ----
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# Copy source and build it
COPY . .

# If using Maven
RUN ./mvnw clean package -DskipTests

# If using Gradle, use:
# RUN ./gradlew build -x test

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto en el que corre la app
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]