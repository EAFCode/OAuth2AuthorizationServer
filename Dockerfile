FROM openjdk:15-jdk-alpine3.11
WORKDIR /
ADD target/server-auth-jwt-bega-0.0.1-SNAPSHOT.jar  server-auth-jwt-bega-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","server-auth-jwt-bega-0.0.1-SNAPSHOT.jar"]