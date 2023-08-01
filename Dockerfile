# Use the official gradle image to create a build artifact.
FROM gradle:7.1.1 as builder

# Copy local code to the container image.
COPY build.gradle.kts .
COPY gradle.properties .
COPY src ./src

# Build a release artifact.
RUN gradle clean installDist

FROM openjdk:18-jdk
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=builder /home/gradle/build/install/gradle /app/
WORKDIR /app/bin
CMD ["./gradle", "ApplicationKt"]
