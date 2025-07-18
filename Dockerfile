FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/nester-0.0.1-SNAPSHOT.jar nester.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/nester.jar"]