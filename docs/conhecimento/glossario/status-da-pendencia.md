---
type: Glossary Term
title: "Status da pendência"
description: "Situações possíveis de uma pendência: OPEN, CLOSED, DISAPPROVED e CANCELED."
status: stable
tags: [glossario]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

| Valor (API) | en | pt-BR | Significado |
|---|---|---|---|
| `OPEN` | Open | Aberta | registrada e atribuída, aguardando execução |
| `CLOSED` | Closed | Concluída | resolvida; exige quem concluiu e quando |
| `DISAPPROVED` | Disapproved | Reprovada | a solução foi reprovada na verificação; exige quem reprovou e quando |
| `CANCELED` | Canceled | Cancelada | descartada; exige quem cancelou e quando |

Os rótulos usam a chave `enum.pendencyStatus.<VALOR>`, a mesma na API e no app.
