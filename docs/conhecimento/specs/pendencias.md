---
type: Specification
title: "Pendências"
description: "Registro e acompanhamento de pendências (punch list) de um contrato, com regras por status e indicadores."
status: stable
capability_id: PEN
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
Uma pendência é um item a corrigir num contrato. Ela tem situação, prioridade e os responsáveis por cada etapa.

# Requisitos

## PEN-001 — Listar por contrato
Quando o cliente listar as pendências de um contrato, então a API devolve uma página (`{content, page}`) ordenada por `createdAt`, com no máximo 100 por página. Cada item traz o contrato e os responsáveis resumidos, sem o contrato aninhado em cada responsável.

## PEN-002 — Criar e atualizar
Dados área (até 120), descrição (até 2000), prioridade (até 20), disciplina (até 60), status, `registeredBy` e `registeredTo` preenchidos, a pendência é criada (201 + `Location`) ou atualizada. Tag (até 50) e datas são opcionais.

## PEN-003 — Campos exigidos por status
Se o status exigir campos ausentes, então a API responde 422:

| Status | Exige |
|---|---|
| `OPEN` | `registeredBy`, `registeredTo` |
| `DISAPPROVED` | `disapprovedBy`, `disapprovedAt` |
| `CANCELED` | `canceledBy`, `canceledAt` |
| `CLOSED` | `finishedBy`, `finishedAt` |

## PEN-004 — Referências inexistentes
Se o contrato ou algum responsável informado não existir, então a API responde 404.

## PEN-005 — Responsáveis do mesmo contrato
Se algum responsável informado pertencer a outro contrato, então a API responde 422.

## PEN-006 — Indicador por status
Quando o cliente pedir o gráfico por status de um contrato, então a API devolve sempre os 4 status, na ordem `OPEN, CLOSED, DISAPPROVED, CANCELED`, com a quantidade de cada um (inclusive 0).

## PEN-007 — Indicador por prioridade
Dado um status válido, a API devolve a quantidade de pendências do contrato nesse status agrupada por prioridade, em ordem de prioridade. Status inválido responde 400.

# Decisões relacionadas
- [ADR-0003 — ProblemDetail](../decisoes/0003-erros-problem-detail-rfc-9457.md)
- [Glossário: status da pendência](../glossario/status-da-pendencia.md)
