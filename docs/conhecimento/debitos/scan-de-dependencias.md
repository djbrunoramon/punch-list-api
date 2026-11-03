---
type: Tech Debt
title: "Sem scan automatizado de dependências"
description: "Não há verificação automática de vulnerabilidades nas dependências Maven nem em PR."
status: stable
severity: média
debt_kind: segurança
debt_status: aberto
tags: [debito]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: origem
    resource: "temp/modernizacao-sb4-ng22/DEBITOS.md#D-SUP-02"
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Onde
- `pom.xml` e `.github/workflows/maven.yml`.

# Impacto
- CVE em dependência transitiva passa despercebida até alguém auditar à mão.

# Sugestão
- Dependabot (Maven e GitHub Actions) e/ou OWASP dependency-check no CI.
