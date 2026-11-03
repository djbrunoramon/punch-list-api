package br.com.engbr.examples.punchlistapi;

import org.springframework.boot.SpringApplication;

/**
 * Runs the application locally against a Testcontainers PostgreSQL (alternative to docker compose).
 */
public class TestPunchListApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(PunchListApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
