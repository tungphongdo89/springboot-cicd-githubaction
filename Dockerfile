FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/springboot_cicd_githubaction.jar springboot_cicd_githubaction.jar
EXPOSE 8088
CMD ["java", "-jar", "springboot_cicd_githubaction.jar"]