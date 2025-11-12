# ---- Build stage ----
FROM gradle:8.10.2-jdk17 AS builder
WORKDIR /app
COPY . .

RUN ./gradlew bootJar -x test

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
ENV PORT=8080
EXPOSE 8080

COPY --from=builder /app/build/libs/la-miga-backend-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar /app/app.jar"]


