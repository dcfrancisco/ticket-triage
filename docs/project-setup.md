# Project Setup Options

## Spring Initializr (CLI)
- Using Spring Boot CLI (if installed):
  ```sh
  spring init \
    --boot-version 3.2.5 \
    --java-version 21 \
    --dependencies=web,validation,data-jpa,flyway,openapi,testcontainers \
    --name=ticket-triage \
    --package-name=com.example.tickettriage \
    ticket-triage
  ```
- Using HTTP + unzip:
  ```sh
  curl -o ticket-triage.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.2.5&baseDir=ticket-triage&groupId=com.example&artifactId=ticket-triage&name=ticket-triage&packageName=com.example.tickettriage&javaVersion=21&dependencies=web,validation,data-jpa,flyway,openapi,testcontainers"
  unzip ticket-triage.zip -d .
  ```

## VS Code
- Install extensions: Spring Initializr Java Support, Spring Boot Extension Pack, Java Pack for VS Code.
- Command Palette → "Spring Initializr: Generate a Maven Project" → choose Java 21, Spring Boot 3.x, group `com.example`, artifact `ticket-triage`, package `com.example.tickettriage`.
- Select dependencies: Web, Validation, Data JPA, Flyway, OpenAPI, Testcontainers.
- Open folder; ensure JDK 21 configured in VS Code Java settings.

## IntelliJ IDEA
- New Project → Spring Initializr → Service URL default.
- Language Java, Type Maven, Java 21, Spring Boot 3.x.
- Group `com.example`, Artifact `ticket-triage`, Package `com.example.tickettriage`.
- Dependencies: Web, Validation, Data JPA, Flyway, Springdoc OpenAPI, Testcontainers.
- After generation: set Project SDK to 21; enable annotation processors; configure Run Configuration with env vars (see docs/environment.md).

## After Generation
- Add Flyway migrations under `src/main/resources/db/migration`.
- Configure `application.yml` for datasource and profiles (read env vars, no secrets in VCS).
- Add `docs/examples.http` for sample requests.
- Run `./mvnw test` with Docker/Colima running for Testcontainers.
