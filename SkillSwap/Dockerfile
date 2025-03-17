FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/SkillSwap-0.0.1-SNAPSHOT.jar SkillSwap.jar

EXPOSE 7070

ENTRYPOINT ["java", "-jar", "SkillSwap.jar"]