# syntax=docker/dockerfile:1

# ---- build ----
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew --version
COPY src src
RUN ./gradlew clean bootJar -x test

# ---- run ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
