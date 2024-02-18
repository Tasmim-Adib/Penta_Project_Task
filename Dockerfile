FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/Penta-0.0.1-SNAPSHOT.jar output.jar
ENTRYPOINT ["java","-jar","/output.jar"]