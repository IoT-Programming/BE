FROM openjdk:17-jdk-slim-buster
COPY ./build/libs/IoTProject-0.0.1-SNAPSHOT.jar app.jar
COPY ./keystore.p12 /app/keystore.p12
EXPOSE 8443
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]