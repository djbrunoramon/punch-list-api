---
type: Architecture Decision
title: "ADR-0007: Records de request/response e mapper manual"
description: "A entrada e a saída da API são records Java mapeados por classes estáticas, sem MapStruct nem BeanUtils."
status: stable
decision: accepted
tags: [adr, contrato, seguranca]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
- O código antigo copiava o DTO para a entidade com `BeanUtils.copyProperties`, o que abre espaço para mass assignment.
- As respostas eram projeções de interface do JPA, acopladas ao repositório.

# Decisão
- `*Request` e `*Response` são `record`, com Bean Validation (`@Size` igual ao tamanho da coluna).
- `*Mapper` final e estático (`toEntity`, `update`, `toResponse`) copia **só** campos editáveis. `id`, `active` e `createdAt` vindos do cliente são ignorados.
- Referência: [`contract/`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/main/java/br/com/engbr/examples/punchlistapi/contract).

# Alternativas consideradas
- **MapStruct:** estava em 1.7.0.Beta2 para o Boot 4 e seria mais uma dependência para três entidades.
- **Projeções de interface:** o contrato fica implícito e preso ao JPA.

# Consequências
- Há testes negativos de mass assignment (POST com `id`/`active`) e de tamanho de campo.
- Campo novo exige alterar o mapper; esquecer o campo aparece como falha de teste de integração.
