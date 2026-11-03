---
type: Specification
title: "Contratos"
description: "Cadastro e consulta de contratos EPC, aos quais responsáveis e pendências pertencem."
status: stable
capability_id: CTR
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
Um contrato agrupa os responsáveis e as pendências de uma obra ou fornecimento EPC.

# Requisitos

## CTR-001 — Listar contratos
Quando o cliente pedir a lista de contratos, então a API devolve todos, ordenados por id, sem paginação.

## CTR-002 — Consultar contrato
Dado um id existente, a API devolve o contrato. Se o id não existir, responde 404 ([Erros da API](erros-da-api.md)).

## CTR-003 — Criar contrato
Dado número (até 50), descrição (até 500) e endereço (até 255) preenchidos, quando o cliente criar o contrato, então ele é salvo como ativo e a resposta é 201 com `Location`. Datas e valor estimado (≥ 0, 2 casas) são opcionais.

## CTR-004 — Campos controlados pelo servidor
Se o cliente enviar `id`, `active` ou `createdAt`, então esses valores são ignorados.

## CTR-005 — Atualizar contrato
Dado um contrato existente e dados válidos, a atualização altera só os campos editáveis. Id inexistente responde 404.

## CTR-006 — Formato de data
As datas saem no formato `yyyy-MM-dd'T'HH:mm`.

# Decisões relacionadas
- [ADR-0007 — Records e mapper manual](../decisoes/0007-records-e-mapper-manual.md)
