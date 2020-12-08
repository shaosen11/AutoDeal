FROM java:8
VOLUME /tmp
ADD /target/deal-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 7777