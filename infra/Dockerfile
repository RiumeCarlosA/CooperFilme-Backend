FROM openjdk:21-jdk-slim

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

RUN ls -l target/

CMD ["java", "-jar", "/app/target/backend-eureka-0.0.1-SNAPSHOT.jar"]
