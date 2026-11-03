---
type: Glossary Term
title: "Pendência"
description: "Item da punch list: algo a corrigir numa área, com prioridade, disciplina, status e responsáveis."
status: stable
tags: [glossario]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

Também chamada de *pendency* no código e na API. Tem:
- área (`areaIdentification`) e tag do equipamento;
- descrição, prioridade (texto livre, por exemplo `A`/`B`) e disciplina (por exemplo Piping, Painting);
- [status](status-da-pendencia.md);
- os responsáveis por registrar (`registeredBy`), executar (`registeredTo`) e concluir, reprovar ou cancelar.

Regras: [spec de pendências](../specs/pendencias.md).
