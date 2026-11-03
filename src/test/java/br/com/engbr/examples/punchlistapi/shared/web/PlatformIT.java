package br.com.engbr.examples.punchlistapi.shared.web;

import br.com.engbr.examples.punchlistapi.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exposure and cross-cutting web configuration (AM-02, AM-03, AM-04, API-02).
 */
@IntegrationTest
class PlatformIT {

    @Autowired
    private MockMvcTester mvc;

    @Test
    void cors_allowedOrigin_preflightSucceeds() {
        assertThat(mvc.options().uri("/api/v1/contract")
                .header(HttpHeaders.ORIGIN, "http://localhost:4200")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
                .hasStatusOk()
                .headers().hasValue(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200");
    }

    @Test
    void cors_unknownOrigin_isRejected() {
        assertThat(mvc.options().uri("/api/v1/contract")
                .header(HttpHeaders.ORIGIN, "https://evil.example")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
                .hasStatus(HttpStatus.FORBIDDEN);
    }

    @Test
    void h2Console_isNotExposedByDefault() {
        assertThat(mvc.get().uri("/h2-console")).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void actuator_exposesOnlyHealth() {
        assertThat(mvc.get().uri("/actuator/health")).hasStatusOk();
        assertThat(mvc.get().uri("/actuator/env")).hasStatus(HttpStatus.NOT_FOUND);
        assertThat(mvc.get().uri("/actuator/beans")).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void openApi_isPublished() {
        assertThat(mvc.get().uri("/api-docs")).hasStatusOk()
                .bodyJson().extractingPath("$.info.title").isEqualTo("Punch-List API");
        assertThat(mvc.get().uri("/swagger-ui/index.html")).hasStatusOk();
    }
}
