FROM maven:3.6.3-jdk-11 as builder

ARG GOOGLE_API_KEY
ARG MAP_API_URL
ARG MYSQL_TEST_HOST
ARG MYSQL_TEST_PORT
ARG MYSQL_TEST_DATABASE
ARG MYSQL_TEST_USERNAME
ARG MYSQL_TEST_PASSWORD
ARG LUCENE_FOLDER
ARG PORT

ENV GOOGLE_API_KEY $GOOGLE_API_KEY
ENV MYSQL_HOST $MYSQL_TEST_HOST
ENV MYSQL_PORT $MYSQL_TEST_PORT
ENV MYSQL_DATABASE $MYSQL_TEST_DATABASE
ENV MYSQL_USERNAME $MYSQL_TEST_USERNAME
ENV MYSQL_PASSWORD $MYSQL_TEST_PASSWORD
ENV LUCENE_FOLDER $LUCENE_FOLDER
ENV PORT $PORT

RUN mkdir $LUCENE_FOLDER

WORKDIR /app

copy . .

RUN mvn clean install

FROM openjdk:11.0.6-slim

ENV PORT 80
ENV MYSQL_PORT 3306
ENV MYSQL_HOST locahost
ENV MYSQL_USERNAME user
ENV MYSQL_PASSWORD pwd
ENV MYSQL_DATABASE db
ENV LUCENE_FOLDER /lucene

WORKDIR /app

RUN mkdir $LUCENE_FOLDER

COPY --from=builder /app/target/chizutsuru-api-0.0.1-rc0.jar /app/api.jar

EXPOSE ${PORT}

CMD ["java", "-jar", "api.jar"]