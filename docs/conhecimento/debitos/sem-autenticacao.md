---
type: Tech Debt
title: "API sem autenticação e autorização"
description: "Qualquer cliente que alcance a API pode ler e alterar todos os dados."
status: stable
severity: alta
debt_kind: segurança
debt_status: aberto
tags: [debito]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: origem
    resource: "temp/modernizacao-sb4-ng22/DEBITOS.md#D-SEC-01 (ameaça AM-09)"
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Onde
- Todos os endpoints de `/api/v1/**`.

# Impacto
- Sem identidade, não há controle de quem cria, altera ou consulta contratos, responsáveis e pendências, nem trilha de auditoria.
- O CORS restrito ([ADR-0003](../decisoes/0003-erros-problem-detail-rfc-9457.md)) só protege contra chamadas de outro site pelo navegador; não substitui autenticação.

# Sugestão
- Spring Security como resource server OIDC (JWT), com papéis por contrato.
- Tarefa própria, com threat model, porque muda o contrato do app (login e token).
