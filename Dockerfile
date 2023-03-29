FROM gradle:8.0.2-jdk17-alpine as builder
WORKDIR /build

COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","-Dsun.net.inetaddr.ttl=0","-Dspring.datasource.url=jdbc:mysql://mysql:3306/jjh?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","app.jar"]