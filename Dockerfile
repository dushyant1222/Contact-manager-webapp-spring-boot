#first we will create the base image same as our java 21 version
FROM eclipse-temurin:21-jre

#Now we will set the workng directory inside the container and everything will now happen in /app
WORKDIR /app

#Now we will copy the jar file which will copy our build Jar
COPY target/*.jar app.jar

#Now we will export the port like i wil tell that this backend is runnig on which port
EXPOSE 8080

#Now we will run the application
ENTRYPOINT ["java","-jar", "app.jar"]

