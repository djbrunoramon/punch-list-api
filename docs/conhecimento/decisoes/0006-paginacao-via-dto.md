---
type: Architecture Decision
title: "ADR-0006: Página no formato estável VIA_DTO"
description: "Listas paginadas usam o formato {content, page:{size,number,totalElements,totalPages}} do Spring Data."
status: stable
decision: accepted
tags: [adr, contrato, paginacao]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
Serializar `PageImpl` direto expõe detalhes internos (`pageable`, `sort`…), e o Spring Data avisa que esse formato não é estável.

# Decisão
- `spring.data.web.pageable.serialization-mode=via-dto` e `max-page-size=100`.
- Corpo: `{"content": [...], "page": {"size", "number", "totalElements", "totalPages"}}`.

# Alternativas consideradas
- **`PageImpl` serializado:** instável entre versões.
- **DTO de página próprio:** reimplementa o que o Spring Data já oferece.

# Consequências
- Quebra de contrato com o app antigo; o app novo usa `Page<T>` nesse formato.
- `size` acima de 100 é limitado a 100 (proteção contra DoS, coberta por teste).
