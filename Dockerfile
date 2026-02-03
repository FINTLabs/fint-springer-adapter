FROM gcr.io/distroless/java21
WORKDIR /app
COPY build/libs/*.jar ./app.jar
CMD ["app.jar"]
