FROM gradle:9.3-jdk21 AS builder
USER root
COPY . .
RUN ./gradlew --no-daemon build

FROM gcr.io/distroless/java21:nonroot
ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError"
COPY --from=builder /home/gradle/build/libs/fint-springer-adapter-*.jar /fint-springer-adapter.jar
CMD ["/data/fint-springer-adapter.jar"]
ENTRYPOINT ["java", "-jar", "/fint-springer-adapter.jar"]