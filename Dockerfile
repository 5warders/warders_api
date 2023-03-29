FROM openjdk:17-jdk-slim
LABEL maintainer="tongher1685@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:mysql://mysql:3306/jjh?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
