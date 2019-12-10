FROM openjdk:8

RUN apt-get update && apt-get install -y maven
COPY . /project
RUN  cd /project && mvn clean package

#run the spring boot application
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod","/project/target/bus-tracker-1.0.0-SNAPSHOT.jar"]