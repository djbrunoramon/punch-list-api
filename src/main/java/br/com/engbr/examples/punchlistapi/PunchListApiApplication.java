package br.com.engbr.examples.punchlistapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PunchListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PunchListApiApplication.class, args);
    }
}
