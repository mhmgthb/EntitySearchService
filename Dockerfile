FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} entity-search-service.jar
ENTRYPOINT ["java" "-jar" "/entity-search-service.jar"]
EXPOSE 9001