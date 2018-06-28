FROM openjdk:8-jdk-alpine
MAINTAINER albertosdlf433@gmail.com
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 17081
EXPOSE 17082
ENV MONGODB_URL=mongodb://172.17.0.5:27017/springboot \
	RABBITMQ_HOST=172.17.0.10 \
	RABBITMQ_PORT=5672 \
	spring.cloud.consul.host=172.17.0.16 \
	spring.cloud.consul.port=8500
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]