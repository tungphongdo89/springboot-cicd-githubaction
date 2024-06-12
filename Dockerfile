FROM public.ecr.aws/docker/library/openjdk:17.0.2-jdk-oraclelinux8
# Copy entry point script
# COPY __cacert_entrypoint.sh /__cacert_entrypoint.sh
# Make sure the script is executable
# RUN chmod +x /__cacert_entrypoint.sh
# Set entry point
# ENTRYPOINT ["/__cacert_entrypoint.sh"]
WORKDIR /app
COPY target/springboot_cicd_githubaction.jar springboot_cicd_githubaction.jar
EXPOSE 8088
CMD ["java", "-jar", "springboot_cicd_githubaction.jar"]