FROM openjdk:11-jre-slim

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /HOME/spring/app.jar 

COPY static/placedata.csv /HOME/spring/static/placedata.csv

WORKDIR /HOME/spring/

EXPOSE 8080