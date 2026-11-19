# Temurin 25.0.5.1 fixes CVE-2026-114938.
FROM gcr.io/distroless/java25-debian13@sha256:3777aeaf78927b8417e2a17f1d6dd20cdb454ea69c7d02fa3f12143d13c0c13a
WORKDIR /app
COPY build/libs/*.jar ./app.jar
CMD ["app.jar"]
