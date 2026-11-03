# AGENTS.md — punch-list-api

Instruções para agentes de código (Claude, Codex, Cursor, Copilot, Gemini etc.). Fonte única: `CLAUDE.md` apenas importa este arquivo.

## Visão geral

API REST de registro de pendências (punch list) de contratos EPC. Backend do front Angular `punch-list-app` (repositório irmão em `../punch-list-app`).

- Java 25, Spring Boot 4.1.2 (namespace `jakarta.*`)
- Spring MVC (`spring-boot-starter-webmvc`), Spring Data JPA, Bean Validation, Actuator
- Liquibase (changelogs em SQL formatado), PostgreSQL 17 (padrão) e H2 em modo PostgreSQL (perfil `h2`)
- Lombok, springdoc-openapi 3.2.0
- Testes: JUnit 5, Testcontainers (PostgreSQL), ArchUnit 1.5.1
- Build: Maven Wrapper (`./mvnw`)

## Comandos

Use JDK 25. No macOS: `export JAVA_HOME=$(/usr/libexec/java_home -v 25)`; em outros ambientes, aponte `JAVA_HOME` para um JDK 25.

```bash
JAVA_HOME=<jdk25> ./mvnw verify                       # build + unidade, WebTest, ArchUnit e IT (precisa de Docker; mesmo que o CI)
JAVA_HOME=<jdk25> ./mvnw test                         # só testes de unidade/fatia web/ArchUnit
JAVA_HOME=<jdk25> ./mvnw test -Dtest=PendencyControllerWebTest            # uma classe
JAVA_HOME=<jdk25> ./mvnw test -Dtest=PendencyControllerWebTest#metodo     # um método
JAVA_HOME=<jdk25> ./mvnw spring-boot:run              # sobe em http://localhost:8080 com Postgres via compose.yaml
JAVA_HOME=<jdk25> ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2    # sem Docker, H2 em memória
```

- Também é possível rodar `TestPunchListApiApplication` (em `src/test`) para subir com Postgres via Testcontainers.
- Swagger UI: http://localhost:8080/swagger-ui/index.html — OpenAPI JSON: `/api-docs` — Health: `/actuator/health` (só `health`, sem detalhes).
- Console H2 (só no perfil `h2`): http://localhost:8080/h2-console (URL `jdbc:h2:mem:punch-list-db`, user `sa`, senha vazia).
- CI: `.github/workflows/maven.yml` usa JDK 25.

## Estrutura

Pacote por feature. Pacote raiz: `br.com.engbr.examples.punchlistapi`

| Pacote | Papel |
|---|---|
| `contract` | Feature Contract: entidade, Request/Response, Mapper, Repository, Service, Controller |
| `responsibleperson` | Feature ResponsiblePerson, mesma composição, mais `ResponsiblePersonSummary` (sem `contract`, usado dentro da pendência) |
| `pendency` | Feature Pendency, mesma composição, mais `PendencyStatus`, `PendencyStatusValidator` e `chart/` (`PendencyByStatus`, `PendencyByPriority`) |
| `shared/error` | `ResourceNotFoundException` (404), `BusinessRuleException` (422) |
| `shared/persistence` | `BaseEntity` (id) |
| `shared/web` | `ApiExceptionHandler`, `CorsConfig`, `CorsProperties`, `OpenApiConfig` |

Entidades: `Contract`, `ResponsiblePerson`, `Pendency` (pertence a um contrato e referencia responsáveis do mesmo contrato). `PendencyStatus`: `OPEN`, `CLOSED`, `DISAPPROVED`, `CANCELED`.

## Padrões a seguir

Arquivos de referência — copie o estilo deles ao criar algo novo:

- Pacote `contract/` é a referência para cada tipo: `Contract` (entidade), `ContractRequest`/`ContractResponse` (records), `ContractMapper`, `ContractRepository`, `ContractService`, `ContractController`.
- Pacote `pendency/` é a referência para regra de negócio (`PendencyStatusValidator`, `PendencyService`) e para referência entre features (`PendencyService.resolve`/`person` usam `ContractService` e `ResponsiblePersonService`, nunca o repositório de outra feature).

Fluxo de um endpoint: `Request` (record com Bean Validation, `@Valid`) → `Mapper` converte para a entidade (só campos editáveis) → `Service` (`@Transactional` na classe, `readOnly = true` nas leituras) salva → `Mapper` devolve o `Response` (record). Controllers nunca expõem entidades; relações entram por id (`Long`).

- Novo recurso: pacote da feature com entidade + Request + Response + Mapper + Repository + Service + Controller + testes + changelog Liquibase.
- Injeção por construtor; sem `@Autowired` em campo.
- Erros: exceções **unchecked** (`ResourceNotFoundException`, `BusinessRuleException`) e `ApiExceptionHandler` (`@RestControllerAdvice`) devolve `ProblemDetail` (`application/problem+json`) com `type` `https://punch-list/errors/{not-found|validation|business-rule|conflict|internal}`. Validação: 400 com `errors: [{field, message}]`; id inexistente: 404; regra de negócio: 422; integridade: 409 com detalhe genérico; inesperado: 500 com `detail: "Unexpected error"`. Nova exceção de domínio: crie em `shared/error` e registre handler em `ApiExceptionHandler`. Nunca devolva `getMessage()` de exceção inesperada ou de persistência.
- `@Operation(summary = "...")` em todo endpoint novo.
- Listagens paginadas usam `Pageable` com `@PageableDefault` e retornam `Page<Response>` (máx. 100 por página).
- Regra de status da pendência: `PendencyStatusValidator` (`OPEN` → registeredBy/registeredTo; `DISAPPROVED` → disapprovedBy/At; `CANCELED` → canceledBy/At; `CLOSED` → finishedBy/At), violação = 422. Responsáveis referenciados devem ser do mesmo contrato da pendência, senão 422; por isso o contrato de um responsável não pode mudar no PUT (422). Mantenha ao alterar o fluxo.
- Código, nomes, mensagens de commit e de erro em inglês, seguindo o restante do projeto.

### Regras do ArchUnit (`ArchitectureTest`)

- Controllers não acessam repositories nem expõem entidades.
- `shared` não depende de nenhuma feature.
- Features sem ciclos entre si.
- Sem injeção em campo (`@Autowired`).
- Repositories só são usados por services (e outros repositories).

## Banco e migrações

- `spring.jpa.hibernate.ddl-auto=validate`: o schema vem **só** do Liquibase. Mudou entidade, crie changelog.
- Arquivos em Liquibase formatted SQL (`--liquibase formatted sql` + `--changeset punch-list:<id>`) em `src/main/resources/db/changelog/sql/`, incluídos em ordem por `changelog-master.xml`. Devem funcionar em PostgreSQL e em H2 (modo PostgreSQL).
- Dados de exemplo em `0100-demo-data.sql`, com `context:demo`; `LIQUIBASE_CONTEXTS` (padrão `demo`) controla se carregam.
- Nunca edite um changeset já aplicado; crie um novo arquivo (ex.: `0101-...sql`) e adicione o `<include>` no master na ordem certa.
- Os testes dependem dos dados demo (ex.: contrato id 1 com 3 pendências `OPEN`). Alterar esses dados quebra asserções.

## Testes

- `PersistenceFlowIT` roda **sem** transação de teste: prova commit e leitura em contexto novo (`open-in-view=false`). Os outros ITs usam `@IntegrationTest` (com rollback).
- `ArchitectureRulesSelfTest` prova que a regra de entidade exposta pega tipos genéricos.

- `*WebTest`: `@WebMvcTest(<Controller>.class)` com `MockMvcTester` e o service como `@MockitoBean`. Cobrem rota, status, validação e formato do erro.
- `*IT`: anotados com `@IntegrationTest` (`@SpringBootTest` + MockMvc + Testcontainers PostgreSQL + `@Transactional`, rollback por teste), contra os dados demo. Rodam no `verify` (Failsafe).
- `ArchitectureTest` (ArchUnit) e `PendencyStatusValidatorTest` (unidade).
- Todo endpoint novo ou bug corrigido ganha teste, incluindo caso de erro (id inexistente 404, payload inválido 400, regra de status 422).

## Pegadinhas conhecidas

- Docker é obrigatório para `./mvnw verify` e para os `*IT` (Testcontainers) e para o `spring-boot:run` padrão (compose). Sem Docker use o perfil `h2` para rodar a app.
- `spring.data.web.pageable.serialization-mode=via-dto`: páginas saem como `{content, page: {size, number, totalElements, totalPages}}`. Não remova; o front depende desse formato.
- CORS é configurado por env: `APP_CORS_ALLOWED_ORIGINS` (padrão `http://localhost:4200`), aplicado a `/api/**` com GET/POST/PUT/OPTIONS e `Location` exposto. Ao hospedar o front em outra origem, ajuste a variável.
- Cada `Response` com data usa `@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")` em cada campo `LocalDateTime`; um novo campo de data na Response precisa da mesma anotação. A entrada aceita ISO local.
- As mensagens de Bean Validation (`errors[].message`) saem no locale da JVM (ex.: pt-BR em JVM em português); não assuma texto fixo em inglês em testes ou no front, compare por `field`.
- O `chart/bar/{status}` com status inválido responde 400 pelo tratamento padrão do Spring MVC.
- Credenciais do `compose.yaml` e do H2 são de desenvolvimento. Não adicione segredos reais em arquivos versionados; use variáveis de ambiente (`SPRING_DATASOURCE_*`).
- `target/` e `.idea/` não devem ser commitados.

## Contrato com o front

O `punch-list-app` consome `/api/v1/contract`, `/api/v1/responsible-person` e `/api/v1/pendency` (incluindo `/pendency/contract/{contractId}/chart/pie` e `/pendency/contract/{contractId}/chart/bar/{status}`). Mudança de rota, formato de data, formato de página, formato de erro ou campo da Response é quebra de contrato: atualize os models/services do front junto.
