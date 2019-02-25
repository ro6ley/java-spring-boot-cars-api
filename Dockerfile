FROM openjdk:8-jdk-alpine

LABEL maintainer="robleyadrian@gmail.com"

EXPOSE 8080

ADD target/cars-0.0.1-SNAPSHOT.jar cars.jar

RUN /bin/sh -c 'touch /cars.jar'

ENTRYPOINT ["java","-Xmx256m","-Djava.security.egd=file:/dev/./urandom","-jar","/cars.jar"]
