---
type: Tech Debt
title: "Virtual threads sem teste que prove o uso"
description: "A propriedade spring.threads.virtual.enabled está ligada, mas nenhum teste confirma que as requisições rodam em virtual threads."
status: stable
severity: baixa
debt_kind: teste
debt_status: aberto
tags: [debito]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: origem
    resource: "temp/modernizacao-sb4-ng22/DEBITOS.md#D-TEST-03"
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Onde
- `application.properties`.

# Impacto
- Uma mudança de configuração poderia desligar o recurso sem ninguém notar.

# Sugestão
- Teste de integração que verifique `Thread.currentThread().isVirtual()` num endpoint exposto só em teste.
