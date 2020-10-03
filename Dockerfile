FROM openjdk:11
ADD ./target/recipe-0.0.1-SNAPSHOT.jar /usr/src/recipe-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "recipe-0.0.1-SNAPSHOT.jar"]