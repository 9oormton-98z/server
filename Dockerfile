FROM openjdk:11-jre-slim

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /HOME/spring/app.jar 

COPY src/main/resources/static /HOME/spring/static

WORKDIR /HOME/spring/

EXPOSE 8080

CMD ["bash", "-c", "java -jar ./app.jar"]