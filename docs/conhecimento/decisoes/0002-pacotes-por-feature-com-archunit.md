---
type: Architecture Decision
title: "ADR-0002: Pacotes por feature com regras ArchUnit"
description: "O código fica organizado por feature (contract, responsibleperson, pendency, shared), com camadas dentro de cada pacote e regras verificadas por ArchUnit."
status: stable
decision: accepted
tags: [adr, arquitetura]
generated: { by: claude-code/claude-opus-5-5, at: 2026-11-03T20:45:00-03:00 }
sources:
  - id: pr-6
    resource: "https://github.com/djbrunoramon/punch-list-api/pull/6"
    title: "Modernização da API (Spring Boot 4.1, Java 25, PostgreSQL, ProblemDetail, i18n)"
  - id: tarefa
    resource: "temp/modernizacao-sb4-ng22 (DECISOES.md, PLANO.md, SPEC-DELTA.md) — branch feature/modernizacao-sb4-ng22"
---

# Contexto
O código antigo estava separado por camada técnica (`controllers/`, `services/`, `model/`…). O usuário pediu "todo o padrão de projeto necessário" para um CRUD de três entidades.

# Decisão
- Um pacote por feature (`contract`, `responsibleperson`, `pendency`), cada um com controller, service, repository, entidade, records de request e response e mapper.
- Código transversal em `shared` (`web`, `error`, `persistence`, `i18n`).
- Uma feature usa outra **pelo service** (`getEntity(id)`), nunca pelo repository.
- As regras ficam em [`ArchitectureTest`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/test/java/br/com/engbr/examples/punchlistapi/ArchitectureTest.java):
  - o controller não acessa repository nem expõe entidade (também em tipos genéricos);
  - `shared` não depende de feature;
  - não há ciclos entre features;
  - não há `@Autowired` em campo;
  - o repository só é usado por service.
- O [`ArchitectureRulesSelfTest`](https://github.com/djbrunoramon/punch-list-api/blob/main/src/test/java/br/com/engbr/examples/punchlistapi/ArchitectureRulesSelfTest.java) prova que a regra de entidade exposta pega `ResponseEntity<List<Entity>>`.

# Alternativas consideradas
- **Hexagonal (ports and adapters):** muitos arquivos e mapeamentos para três entidades CRUD. Rejeitada pelo usuário.
- **Manter as camadas técnicas:** espalha cada mudança de feature por vários pacotes.
- **Spring Modulith:** dependência extra; o ArchUnit cobre as regras necessárias.

# Consequências
- Feature nova = pacote novo, espelhando `contract/` (referência no AGENTS.md).
- A revisão independente achou uma brecha na regra (entidade dentro de genérico); ela foi corrigida e coberta pelo self-test.
