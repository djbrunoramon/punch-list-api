---
type: Architecture Decision
title: "ADR-0004: PostgreSQL com baseline Liquibase reescrita"
description: "PostgreSQL 17 vira o banco padrão (compose em dev e Testcontainers nos testes), com uma baseline Liquibase nova compatível com PostgreSQL e H2."
status: stable
decision: accepted
tags: [adr, banco, liquibase]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
O banco sempre foi H2 em memória: nenhum dado persistiu em ambiente nenhum. O SQL antigo (`datetime`, `interval 1 day`, ids explícitos) não roda no PostgreSQL.

# Decisão
- **PostgreSQL 17:** [`compose.yaml`](https://github.com/djbrunoramon/punch-list-api/blob/main/compose.yaml), que o `spring-boot-docker-compose` sobe sozinho no `spring-boot:run`, e Testcontainers nos testes (`@ServiceConnection`).
- **H2 só no perfil `h2`**, em modo PostgreSQL, para rodar sem Docker. O console H2 fica só nesse perfil.
- **Baseline reescrita** em `db/changelog/sql/0001..0004` (formatted SQL com rollback), com os **mesmos nomes de tabela e coluna**, `varchar(n)` alinhados ao `@Size` e índices novos.
- **Dados de exemplo** em `0100-demo-data.sql`, com `context:demo` (`LIQUIBASE_CONTEXTS`, padrão `demo`).
- `ddl-auto=validate`.

# Alternativas consideradas
- **Manter H2:** não representa produção.
- **Editar os changesets antigos para PostgreSQL:** a regra é nunca editar changeset aplicado. Como o banco nunca persistiu, substituir a baseline é seguro e deixa o histórico limpo.

# Consequências
- `./mvnw verify` exige Docker.
- Daqui em diante vale a regra: **nunca editar changeset aplicado**; crie `0005-...` em diante.
- Sem `LIQUIBASE_CONTEXTS`, ou com ele vazio, o Liquibase roda todos os changesets, inclusive os de demo.
