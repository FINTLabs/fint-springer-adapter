FROM gradle:7.4.2-jdk8 as builder
USER root
COPY . .
RUN gradle --no-daemon build

FROM gcr.io/distroless/java:8
ENV JAVA_TOOL_OPTIONS -XX:+ExitOnOutOfMemoryError
COPY --from=builder /home/gradle/build/libs/fint-springer-adapter-*.jar /data/fint-springer-adapter.jar
CMD ["/data/fint-springer-adapter.jar"]
