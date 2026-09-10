FROM gcr.io/distroless/java25-debian13
WORKDIR /app
COPY build/libs/*.jar ./app.jar
CMD ["app.jar"]
