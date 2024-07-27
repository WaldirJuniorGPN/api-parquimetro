FROM maven:3.8.3-openjdk-17-slim AS build
COPY /src /api-parquimetro/src
COPY /pom.xml /api-parquimetro
WORKDIR /api-parquimetro
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /api-parquimetro/${JAR_FILE} /api-parquimetro/api-parquimetro.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api-parquimetro/api-parquimetro.jar"]