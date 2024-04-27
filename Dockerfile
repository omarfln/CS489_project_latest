FROM maven:3.8.4-openjdk-17 as build

COPY ./ /usr/src/app

WORKDIR /usr/src/app

RUN mvn clean package -DskipTests

FROM amazoncorretto:17

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

WORKDIR /usr/app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
