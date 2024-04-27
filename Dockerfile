# Use maven:3.8.4-openjdk-17 to build the application
FROM maven:3.8.4-openjdk-17 as build
COPY ./ /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

# Use amazoncorretto:17 to run the application, but also include Maven for testing
FROM amazoncorretto:17
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
COPY --from=build /usr/share/maven /usr/share/maven
ENV PATH="/usr/share/maven/bin:${PATH}"
WORKDIR /usr/app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

