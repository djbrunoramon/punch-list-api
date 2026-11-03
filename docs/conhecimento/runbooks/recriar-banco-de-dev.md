---
type: Runbook
title: "Recriar o banco PostgreSQL de desenvolvimento"
description: "Como apagar e recriar o banco local do compose quando o histórico do Liquibase estiver inconsistente."
status: stable
tags: [operacao]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Quando usar
- A API falha no start com `relation "..." already exists` ou com um changeset reaplicado.
- O banco local ficou com dados de teste que você quer descartar.

# Passos
1. Pare a API.
2. Apague o container **e o volume**. Isso apaga **todos** os dados locais: `docker compose down -v`.
3. Suba de novo: `./mvnw spring-boot:run`. O Liquibase recria o schema e os dados `demo`.

# Como confirmar
- Pare e suba a API duas vezes seguidas: os dois starts devem chegar a `Started PunchListApiApplication`.

# Causa conhecida
- [ADR-0005 — Histórico do Liquibase no schema public](../decisoes/0005-historico-liquibase-no-schema-public.md)
