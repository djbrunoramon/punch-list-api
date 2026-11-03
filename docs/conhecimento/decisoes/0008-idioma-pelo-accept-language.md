---
type: Architecture Decision
title: "ADR-0008: Idioma das respostas pelo Accept-Language"
description: "A API responde em en (padrão) ou pt-BR conforme o Accept-Language, nunca pelo locale da JVM, com mensagens em messages*.properties."
status: stable
decision: accepted
tags: [adr, i18n]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa-i18n
    resource: "temp/i18n (DEMANDA.md, PADRAO-NOMENCLATURA.md, RELATORIO.md) — branch feature/modernizacao-sb4-ng22"
  - id: commits
    resource: "eaae3a4, 9d48478"
---

# Contexto
- As mensagens de validação saíam no locale da JVM (pt-BR na máquina de dev), enquanto o app estava em inglês.
- O usuário pediu i18n em pt-BR e en, com padrão de nomenclatura de chaves.

# Decisão
- O [`LanguageLocaleResolver`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/main/java/br/com/engbr/examples/punchlistapi/shared/i18n/LanguageLocaleResolver.java) casa o idioma pela língua (`pt`, `pt-PT` e `pt-BR` → `pt-BR`). Qualquer outro valor vira `en`.
- O `ContentLanguageFilter` informa o idioma em toda resposta.
- `messages.properties` (en) e `messages_pt_BR.properties`, com `fallback-to-system-locale=false`.
- As exceções de domínio implementam `MessageSourceResolvable` (código + argumentos).
- O padrão de chaves está em [i18n](../modulos/i18n.md).
- Os testes rodam com a JVM em `pt_BR` (surefire/failsafe) para provar a independência do locale da JVM.

# Alternativas consideradas
- **`AcceptHeaderLocaleResolver` do Spring:** com `pt-BR` na lista de suportados, não casa `pt-PT`/`pt`; o teste falhou com ele.
- **Traduzir só no front pelo `type`:** perde o detalhe das regras de negócio e das mensagens de validação.

# Consequências
- O `type`, o `status` e o `errors[].field` não mudam com o idioma: o front usa esses campos para lógica, nunca o texto.
- Os rótulos de status e de campo nas mensagens usam as mesmas chaves `enum.*` do app.
