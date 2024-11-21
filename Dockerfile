FROM gradle:jdk22-alpine AS build

WORKDIR /home/gradle/project

COPY build.gradle settings.gradle /home/gradle/project/
COPY src /home/gradle/project/src

RUN gradle clean build --no-daemon

FROM eclipse-temurin:22-jre-alpine

RUN apk add --no-cache bash curl && \
    rm -rf /var/cache/apk/*

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/db-practice.jar /app/app.jar

RUN chown -R appuser:appgroup /app

USER appuser

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

