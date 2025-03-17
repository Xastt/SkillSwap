FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/SkillSwapMailSender-0.0.1-SNAPSHOT.jar SkillSwapMailSender.jar

EXPOSE 7071

ENTRYPOINT ["java", "-jar", "SkillSwapMailSender.jar"]