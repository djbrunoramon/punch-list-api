---
type: Architecture Decision
title: "ADR-0001: Spring Boot 4.1 e Java 25"
description: "A API passou de Spring Boot 2.6 / Java 11 para Spring Boot 4.1.2 / Java 25."
status: stable
decision: accepted
tags: [adr, plataforma]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
A API estava em Spring Boot 2.6.4 e Java 11 e não compilava mais em JDK 21+, por causa de um Lombok antigo. O `javax.*` e o springdoc 1.x estavam fora de suporte. O app vizinho também seria modernizado na mesma tarefa.

# Decisão
Usar **Spring Boot 4.1.2** (a última estável em 2026-11-03), **Java 25**, starters modulares (`spring-boot-starter-webmvc`, `-liquibase`, `-*-test`), Jackson 3, springdoc 3.2.0 e o Maven wrapper 3.3.4.

# Alternativas consideradas
- **Spring Boot 4.2.0-RC1:** descartada por ser release candidate.
- **Spring Boot 3.5 / Java 21:** menos mudança, mas sairia de suporte antes e não usa os starters modulares nem o Jackson 3.
- **Migração incremental (2.6 → 3.x → 4.x):** sem ganho, porque o código foi reescrito por feature de qualquer forma ([ADR-0002](0002-pacotes-por-feature-com-archunit.md)).

# Consequências
- Exige JDK 25 em dev e no CI (`.github/workflows/maven.yml`).
- Virtual threads ligadas (`spring.threads.virtual.enabled=true`), ainda sem teste dedicado ([débito](../debitos/virtual-threads-sem-teste.md)).
- Os testes usam os pacotes novos do Boot 4: `org.springframework.boot.webmvc.test.autoconfigure`, `@MockitoBean` e `MockMvcTester`.
