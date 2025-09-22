# ---- Stage 1: Build the application with Maven ----
# We use a multi-platform base image that works on both standard computers (x86) and Raspberry Pi (ARM).
# This stage uses a full JDK and Maven to build the project.
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml to download dependencies first, leveraging Docker's layer caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Package the application into a single, executable "fat" JAR.
# The shade plugin you configured will bundle all dependencies.
RUN mvn package -DskipTests


# ---- Stage 2: Create the final, lightweight runtime image ----
# This stage uses a much smaller Java Runtime Environment (JRE) image,
# which is also multi-platform for Raspberry Pi compatibility.
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy only the compiled JAR file from the builder stage
COPY --from=builder /app/target/jdaTest-1.0-SNAPSHOT.jar app.jar

# This is the command that will run when the container starts
CMD ["java", "-jar", "app.jar"]