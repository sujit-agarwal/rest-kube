FROM openjdk:17.0.2-jdk-buster as build
ADD ./target/*.jar app.jar
CMD java -jar -Dloader.path=file:///config/application.properties app.jar