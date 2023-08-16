FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]