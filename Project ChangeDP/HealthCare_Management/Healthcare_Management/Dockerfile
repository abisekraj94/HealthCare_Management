# =========================
# Stage 1: Build the application
# =========================
FROM eclipse-temurin:21-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# ✅ Fix permission
RUN chmod +x mvnw

# Copy local JAR dependency
COPY lib/Multitenant-0.0.1-SNAPSHOT.jar lib/

# Install the JAR into the local Maven repository
RUN ./mvnw install:install-file \
  -Dfile=lib/Multitenant-0.0.1-SNAPSHOT.jar \
  -DgroupId=com.prd \
  -DartifactId=Multitenant \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackaging=jar

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# =========================
# Stage 2: Runtime container
# =========================
FROM eclipse-temurin:21-jre-jammy AS runtime
# For smaller image, use:
# FROM eclipse-temurin:17-jre-alpine AS runtime

# Set working directory
WORKDIR /app

# Create non-root user
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/users/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
