FROM openjdk:11.0.16
WORKDIR /app
COPY ./target/api-cuentas-0.0.1-SNAPSHOT.jar .
EXPOSE 8083
ENTRYPOINT ["java","-jar","api-cuentas-0.0.1-SNAPSHOT.jar"]


