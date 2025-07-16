FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM node:20-bullseye-slim AS runtime

# Устанавливаем Java (openjdk 17) в образ Node
RUN apt-get update && apt-get install -y openjdk-17-jdk && \
    npm install -g jsonrepair && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
