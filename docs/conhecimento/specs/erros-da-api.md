---
type: Specification
title: "Erros da API"
description: "Formato, status HTTP e idioma das respostas de erro."
status: stable
capability_id: ERR
tags: [spec]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
  - id: delta
    resource: "temp/modernizacao-sb4-ng22/SPEC-DELTA.md (reconciliado)"
---

# Propósito
Garantir erros previsíveis, sem vazamento de informação interna e no idioma do cliente.

# Requisitos

## ERR-001 — Formato
Toda resposta de erro usa `application/problem+json`, com `type`, `title`, `status`, `detail` e `instance`.

## ERR-002 — Status por situação
- Recurso inexistente: 404.
- Payload inválido: 400, com `errors[{field, message}]`.
- Regra de negócio: 422.
- Violação de integridade: 409.
- Erro inesperado: 500.

## ERR-003 — Sem vazamento
Em erros 500 e 409, o corpo não contém mensagem de exceção, SQL, nome de classe nem stack.

## ERR-004 — Idioma
Quando o cliente enviar `Accept-Language` com `pt` (qualquer variante), então `title`, `detail` e `errors[].message` saem em português do Brasil. Sem o header, ou com outro idioma (inclusive valor malformado), a resposta sai em inglês, independentemente do locale do servidor.

## ERR-005 — Idioma informado
Toda resposta leva `Content-Language` com o idioma usado (`en` ou `pt-BR`).

## ERR-006 — Campos estáveis
`type`, `status` e `errors[].field` não variam com o idioma.

# Decisões relacionadas
- [ADR-0003](../decisoes/0003-erros-problem-detail-rfc-9457.md)
- [ADR-0008](../decisoes/0008-idioma-pelo-accept-language.md)
