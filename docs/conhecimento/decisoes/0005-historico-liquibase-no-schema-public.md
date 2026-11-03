---
type: Architecture Decision
title: "ADR-0005: Histórico do Liquibase fixo no schema public"
description: "spring.liquibase.liquibase-schema=public evita que o histórico de migrações se divida entre dois schemas no PostgreSQL."
status: stable
decision: accepted
tags: [adr, banco, liquibase, incidente]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: commit
    resource: "81dd2fa — fix: pin Liquibase history to the public schema"
---

# Contexto
No 2º start com o compose, a API falhava com `relation "contract" already exists`.
- O usuário do banco se chama `punch_list`, igual ao schema da aplicação.
- O `search_path` padrão do PostgreSQL é `"$user", public`. Quando o changeset 0001 cria o schema `punch_list`, o `"$user"` passa a resolver para ele.
- O Liquibase então cria um segundo `databasechangelog` em `punch_list`, não acha o histórico gravado em `public` e tenta reaplicar tudo.
- Os testes não pegavam o problema porque o Testcontainers usava o usuário `test`.

# Decisão
- `spring.liquibase.liquibase-schema=public` em [`application.properties`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/main/resources/application.properties).
- O Testcontainers passa a usar os mesmos nomes do compose (`punch_list`).
- O [`LiquibaseHistoryIT`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/test/java/br/com/engbr/examples/punchlistapi/LiquibaseHistoryIT.java) **reexecuta** a migração. Ele falhava com a mesma mensagem antes da correção.

# Alternativas consideradas
- **Renomear o usuário do compose:** esconde o problema, e ele voltaria em qualquer ambiente com usuário igual ao schema.
- **`default-schema=punch_list`:** exige o schema antes da primeira migração e mistura histórico com tabelas de negócio.

# Consequências
- O histórico fica sempre em `public.databasechangelog`.
- Banco de dev que já tenha o histórico dividido precisa ser recriado ([runbook](../runbooks/recriar-banco-de-dev.md)).
