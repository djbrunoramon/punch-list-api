---
type: Runbook
title: "Adicionar um idioma à API"
description: "Passos para incluir um novo idioma nas respostas da API."
status: stable
tags: [operacao]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
---

# Passos
1. Crie `src/main/resources/messages_<ll>_<CC>.properties` com **todas** as chaves de `messages.properties`.
2. Inclua o locale em `I18nConfig.SUPPORTED_LOCALES`.
3. Inclua o arquivo no `MessagesParityTest`.
4. Adicione casos no `I18nIT`: um 404, um 400 e um 422 no novo idioma.
5. Faça o mesmo no app (`docs/conhecimento/runbooks/adicionar-idioma.md` do punch-list-app).

# Como confirmar
- `./mvnw verify` verde.
- `curl -H 'Accept-Language: <tag>' localhost:8080/api/v1/contract/999` responde no novo idioma, com `Content-Language` correspondente.

# Referência
- [i18n](../modulos/i18n.md)
