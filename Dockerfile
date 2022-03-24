FROM gradle:jdk-alpine AS builder

WORKDIR /backoffice_batch

USER root

RUN apk update

ENV GRADLE_USER_HOME /backoffice_batch

COPY . /backoffice_batch

RUN gradle build

FROM openjdk:8-jdk-alpine

COPY --from=builder /backoffice_batch/build /build

EXPOSE 8080
#ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-Dlog4j2.formatMsgNoLookups=true","-jar","/app.jar","--spring.config.location=/config/application.properties,/config/kakaobizmessage.yml"]
