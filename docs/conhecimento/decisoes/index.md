# Decisões

* [ADR-0001: Spring Boot 4.1 e Java 25](0001-spring-boot-4-e-java-25.md) - A API passou de Spring Boot 2.6 / Java 11 para Spring Boot 4.1.2 / Java 25.
* [ADR-0002: Pacotes por feature com regras ArchUnit](0002-pacotes-por-feature-com-archunit.md) - O código fica organizado por feature (contract, responsibleperson, pendency, shared), com camadas dentro de cada pacote e regras verificadas por ArchUnit.
* [ADR-0003: Erros no formato ProblemDetail (RFC 9457)](0003-erros-problem-detail-rfc-9457.md) - Toda resposta de erro usa application/problem+json com status HTTP corretos e sem expor mensagens internas.
* [ADR-0004: PostgreSQL com baseline Liquibase reescrita](0004-postgresql-e-baseline-liquibase.md) - PostgreSQL 17 vira o banco padrão (compose em dev e Testcontainers nos testes), com uma baseline Liquibase nova compatível com PostgreSQL e H2.
* [ADR-0005: Histórico do Liquibase fixo no schema public](0005-historico-liquibase-no-schema-public.md) - spring.liquibase.liquibase-schema=public evita que o histórico de migrações se divida entre dois schemas no PostgreSQL.
* [ADR-0006: Página no formato estável VIA_DTO](0006-paginacao-via-dto.md) - Listas paginadas usam o formato {content, page:{size,number,totalElements,totalPages}} do Spring Data.
* [ADR-0007: Records de request/response e mapper manual](0007-records-e-mapper-manual.md) - A entrada e a saída da API são records Java mapeados por classes estáticas, sem MapStruct nem BeanUtils.
* [ADR-0008: Idioma das respostas pelo Accept-Language](0008-idioma-pelo-accept-language.md) - A API responde em en (padrão) ou pt-BR conforme o Accept-Language, nunca pelo locale da JVM, com mensagens em messages*.properties.
