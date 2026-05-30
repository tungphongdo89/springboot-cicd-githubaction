 FROM public.ecr.aws/docker/library/openjdk:17.0.2-jdk-oraclelinux8

 #FROM eclipse-temurin:17-jdk-alpine
 WORKDIR /app
 COPY target/springboot_cicd_githubaction.jar springboot_cicd_githubaction.jar
 EXPOSE 8088
 CMD ["java", "-jar", "springboot_cicd_githubaction.jar"]


## =================================== enhance =====================================
## syntax=docker/dockerfile:1.7
#############################
## Build stage
#############################
#FROM maven:3.9.9-eclipse-temurin-17 AS builder
#WORKDIR /build
#
## Copy pom first for better layer caching
#COPY pom.xml .
#RUN mvn -B -q dependency:go-offline
#
## Copy source and build
#COPY src ./src
#RUN mvn -B -q clean package -DskipTests
#
#############################
## Runtime stage
#############################
#FROM eclipse-temurin:17-jre-jammy AS runtime
#WORKDIR /app
#ENV TZ=Asia/Bangkok
#ENV JAVA_OPTS=""
#ENV SERVER_PORT=8080
#
## Security: run as non-root user
#RUN useradd -r -u 10001 appuser
#
## Copy only the built jar
#COPY --from=builder /build/target/*.jar /app/app.jar
#
## Basic container healthcheck
#HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
#  CMD curl -fsS http://localhost:${SERVER_PORT}/actuator/health || exit 1
#
#EXPOSE 8080
#USER 10001
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${SERVER_PORT}"]