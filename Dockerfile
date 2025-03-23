FROM openjdk:17-jdk-slim
LABEL authors="AngelJs"
ARG JAR_FILE=target/DentalClinicMVC-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} dentalClinic.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dentalClinic.jar"]