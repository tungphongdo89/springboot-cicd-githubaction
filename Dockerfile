#FROM public.ecr.aws/docker/library/openjdk:17.0.2-jdk-oraclelinux8
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/springboot_cicd_githubaction.jar springboot_cicd_githubaction.jar
EXPOSE 8088
CMD ["java", "-jar", "springboot_cicd_githubaction.jar"]