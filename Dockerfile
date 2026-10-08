# ---------- Build ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true


# ---------- Runtime ----------
FROM bellsoft/liberica-openjre-debian:21-cds

RUN addgroup --system spring-boot-group \
    && adduser --system --ingroup spring-boot-group spring-boot

WORKDIR /application

COPY --from=builder /app/target/*.jar application.jar

USER spring-boot:spring-boot-group

ENTRYPOINT ["java", "-jar", "application.jar"]
