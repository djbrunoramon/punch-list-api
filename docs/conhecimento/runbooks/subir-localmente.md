---
type: Runbook
title: "Subir a API localmente"
description: "Como rodar a API com PostgreSQL (compose), H2 ou Testcontainers."
status: stable
tags: [operacao]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Pré-requisitos
- JDK 25 (`export JAVA_HOME=$(/usr/libexec/java_home -v 25)` no macOS).
- Docker, para o PostgreSQL e os testes.

# Passos
1. PostgreSQL via compose, que sobe e desce junto com a aplicação: `./mvnw spring-boot:run`.
2. Sem Docker (H2 em memória, com console em `/h2-console`): `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`.
3. Com Testcontainers: rode a classe `TestPunchListApiApplication` (em `src/test`) pela IDE.

# Como confirmar
- `curl localhost:8080/actuator/health` deve responder `{"status":"UP"}`.
- Swagger em http://localhost:8080/swagger-ui/index.html.
- Os dados de exemplo trazem o contrato `BR2022-001` (contexto Liquibase `demo`).

# App junto
No `punch-list-app`, rode `npm start`. O proxy encaminha `/api` para a porta 8080.
