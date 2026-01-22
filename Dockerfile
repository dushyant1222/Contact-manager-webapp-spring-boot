#first we will create the base image same as our java 21 version
FROM maven:3.9.9-eclipse-temurin-17 AS build

#Now we will set the workng directory inside the container and everything will now happen in /app
WORKDIR /app

#Now we will copy the jar file which will copy our build Jar
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

#<-------------RUN STAGE----------->
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]

