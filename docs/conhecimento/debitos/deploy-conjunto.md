---
type: Tech Debt
title: "Deploy da API e do app precisa ser conjunto"
description: "O deploy antigo (Elastic Beanstalk e GitHub Pages) não acompanha o novo contrato de erro, página e idioma."
status: stable
severity: média
debt_kind: operação
debt_status: aberto
tags: [debito]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: origem
    resource: "temp/modernizacao-sb4-ng22/DEBITOS.md#D-OPS-01"
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Onde
- `environment.prod.ts` do app (`apiBaseUrl` vazio) e o `docs/` do app (build Angular 13 antigo).
- Deploy da API sem pipeline.

# Impacto
- Publicar só um dos lados quebra a integração: formato ProblemDetail, página `{content, page}` e `Accept-Language`.

# Sugestão
- Pipeline de deploy conjunto, com `APP_CORS_ALLOWED_ORIGINS` configurado e banco PostgreSQL gerenciado.
