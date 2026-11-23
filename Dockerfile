FROM gcr.io/distroless/java25-debian13@sha256:d1b024b25b9dd0a363c14e2927e8059c94bff102f2e2a645eb849ea36766292a
WORKDIR /app
COPY build/libs/*.jar ./app.jar
CMD ["app.jar"]
