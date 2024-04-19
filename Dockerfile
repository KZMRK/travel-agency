FROM openjdk:17-jdk-alpine
ARG JAR_FILE=./target/*.jar
ENV INIT_MODE=never
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.sql.init.mode=${INIT_MODE}", "/app.jar"]