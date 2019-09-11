FROM openjdk:8-jdk
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java  $JAVA_OPTS -jar /app.jar --spring.config.location=$CONFIG_LOCATION" ]