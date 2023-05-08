FROM maven:3.9.1-ibmjava AS build
COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip



FROM openjdk:17
EXPOSE 9090
ADD target/astrology.jar astrology.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]