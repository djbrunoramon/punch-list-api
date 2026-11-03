package br.com.engbr.examples.punchlistapi.shared.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(
        title = "Punch-List API",
        version = "v1",
        description = "Punch list registration for EPC contracts - by Bruno Ramon (github @djbrunoramon)"))
public class OpenApiConfig {
}
