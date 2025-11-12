# ---- Build stage ----
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
# Обязательно делаем gradlew исполняемым, иначе внутри контейнера команда не запустится
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV PORT=8080
EXPOSE 8080

COPY --from=builder /app/build/libs/la-miga-backend-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar /app/app.jar"]


