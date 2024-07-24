FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

# Agregar argumento para el perfil de Spring
ARG PROFILE=default
ENV SPRING_PROFILES_ACTIVE=${PROFILE}

CMD ["./mvnw", "spring-boot:run"]
