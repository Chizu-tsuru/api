FROM maven:3.6.3-jdk-11 as builder

ARG GOOGLE_API_KEY_ARG
ARG MAP_API_URL_ARG

ENV PORT 80
ENV MYSQL_PORT 3306
ENV MYSQL_HOST locahost
ENV MYSQL_USERNAME user
ENV MYSQL_PASSWORD pwd
ENV MYSQL_DATABASE db
ENV GOOGLE_API_KEY $GOOGLE_API_KEY_ARG
ENV MAP_API_URL $MAP_API_URL_ARG

WORKDIR /app

copy . .

RUN mvn clean install

FROM openjdk:11.0.6-slim

WORKDIR /app

COPY --from=builder /app/target/chizutsuru-api-0.0.1-rc0.jar /app/api.jar

EXPOSE ${PORT}

CMD ["java", "-jar", "api.jar"]