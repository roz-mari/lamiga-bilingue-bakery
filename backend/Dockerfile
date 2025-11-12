# ---- Build stage ----
FROM gradle:8.10.2-jdk17 AS builder
WORKDIR /app
COPY . .
# Сборка jar без тестов для ускорения
RUN ./gradlew bootJar -x test

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
ENV PORT=8080
EXPOSE 8080
# Копируем собранный jar (имя соответствует Gradle-артефакту)
COPY --from=builder /app/build/libs/la-miga-backend-0.0.1-SNAPSHOT.jar /app/app.jar
# Render передаёт порт в переменной PORT — пробрасываем его в Spring Boot
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar /app/app.jar"]


