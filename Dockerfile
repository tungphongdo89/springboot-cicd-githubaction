#FROM public.ecr.aws/docker/library/amazoncorretto:21
#WORKDIR /app
#COPY target/springboot_cicd_githubaction.jar springboot_cicd_githubaction.jar
#EXPOSE 8088
#CMD ["java", "-jar", "springboot_cicd_githubaction.jar"]


# =================================== enhance =====================================
# syntax=docker/dockerfile:1.7
##############################
# Build stage
##############################
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

##############################
# Runtime stage
##############################
FROM amazoncorret
WORKDIR /app
ENV JAVA_OPTS=""
ENV SERVER_PORT=8088
COPY --from=builder /build/target/*.jar /app/app.jar
EXPOSE 8088
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar --server.port=${SERVER_PORT}"]