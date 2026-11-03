---
type: Architecture Decision
title: "ADR-0003: Erros no formato ProblemDetail (RFC 9457)"
description: "Toda resposta de erro usa application/problem+json com status HTTP corretos e sem expor mensagens internas."
status: stable
decision: accepted
tags: [adr, erros, contrato]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: pr-app-7
    resource: "https://github.com/djbrunoramon/punch-list-app/pull/7"
    title: "Modernização do app (consumidor da API)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
Antes, todo erro era 400 com `ResponseError` e repetia `e.getMessage()`, inclusive SQL e mensagens do parser, para o cliente.

# Decisão
O [`ApiExceptionHandler`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/main/java/br/com/engbr/examples/punchlistapi/shared/web/ApiExceptionHandler.java) estende `ResponseEntityExceptionHandler` e devolve `ProblemDetail`:

| Situação | Status | `type` |
|---|---|---|
| Recurso inexistente | 404 | `https://punch-list/errors/not-found` |
| Payload inválido | 400 + `errors[{field,message}]` | `.../validation` |
| Regra de negócio | 422 | `.../business-rule` |
| Violação de integridade | 409, detail genérico | `.../conflict` |
| Erro inesperado | 500, `Unexpected error` | `.../internal` |

As exceções de domínio (`ResourceNotFoundException`, `BusinessRuleException`) não são checadas e carregam código de mensagem ([ADR-0008](0008-idioma-pelo-accept-language.md)).

# Alternativas consideradas
- **Manter `ResponseError`:** sem padrão, status errado e vazamento de informação. Rejeitada pelo usuário.
- **Formato próprio:** reinventa o RFC 9457, que o Spring já suporta nativamente.

# Consequências
- Quebra de contrato: o app foi migrado no mesmo trabalho (punch-list-app PR #7).
- O 400 de conversão do Spring (status inválido no path) usa `type` `about:blank`.
- Comportamento detalhado em [Erros da API](../specs/erros-da-api.md).
