FROM openjdk:17-jdk-slim
LABEL authors="AngelJs"

ARG JAR_FILE=DentalClinicMVC-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]