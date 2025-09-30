FROM eclipse-temurin:17

LABEL mentainer="kduy2019@gmail.com"

WORKDIR /app

COPY target/writing-grading-system-0.0.1-SNAPSHOT.jar /app/writing-grading-system.jar

ENTRYPOINT ["java", "-jar", "writing-grading-system.jar"]