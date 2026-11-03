---
type: Specification
title: "Responsáveis"
description: "Pessoas responsáveis de um contrato, que registram e recebem pendências."
status: stable
capability_id: RSP
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
Um responsável pertence a um contrato e pode registrar, receber, concluir, reprovar ou cancelar pendências desse contrato.

# Requisitos

## RSP-001 — Listar por contrato
Quando o cliente listar os responsáveis de um contrato, então a API devolve uma página (`{content, page}`) ordenada por `createdAt` ascendente, com 20 por página por padrão e no máximo 100.

## RSP-002 — Criar responsável
Dado `idContract` de um contrato existente e nome, cargo e departamento preenchidos (até 120 cada), o responsável é criado ativo (201 + `Location`). Contrato inexistente responde 404.

## RSP-003 — Contrato imutável
Se uma atualização trouxer um `idContract` diferente do contrato atual do responsável, então a API responde 422 e nada é alterado.

## RSP-004 — Consultar e atualizar
Id inexistente responde 404. A resposta inclui o contrato do responsável.

# Decisões relacionadas
- [Pendências](pendencias.md), PEN-005 (motivo da RSP-003)
