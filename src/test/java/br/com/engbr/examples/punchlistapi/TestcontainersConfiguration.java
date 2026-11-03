package br.com.engbr.examples.punchlistapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        // same database/user names as compose.yaml: the user name equals the schema name, which changes
        // the "$user" entry of the PostgreSQL search_path once the schema exists (see LiquibaseHistoryIT)
        return new PostgreSQLContainer(DockerImageName.parse("postgres:17-alpine"))
                .withDatabaseName("punch_list")
                .withUsername("punch_list")
                .withPassword("punch_list");
    }
}
