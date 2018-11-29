FROM openjdk:8-jdk-alpine
LABEL maintainer="javier.ramos1@gmail.com"
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} streamapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/streamapp.jar"]