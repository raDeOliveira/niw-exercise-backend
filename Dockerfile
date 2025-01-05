FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
