package br.com.engbr.examples.punchlistapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
@Info(
        title = "Punch-List API",
        version = "v1",
        description = "Documentation of Punch-List API - By Bruno Ramon - github (@djbrunoramon)"))
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                .title("Punch-List API")
                .version("v1"));
    }
}
