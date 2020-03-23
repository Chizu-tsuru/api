FROM maven:3.6.3-jdk-11 as builder

WORKDIR /app

copy . .

RUN mvn clean install

RUN ls ./target
FROM openjdk:11.0.6-slim

WORKDIR /app

COPY --from=builder /app/target/mapinator-0.0.1-rc0.jar /app/api.jar

RUN ls

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]