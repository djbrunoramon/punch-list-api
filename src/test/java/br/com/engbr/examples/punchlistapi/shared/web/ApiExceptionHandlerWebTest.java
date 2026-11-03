package br.com.engbr.examples.punchlistapi.shared.web;

import br.com.engbr.examples.punchlistapi.contract.ContractController;
import br.com.engbr.examples.punchlistapi.contract.ContractService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * AM-01: unexpected and persistence errors never leak internal messages.
 */
@WebMvcTest(ContractController.class)
class ApiExceptionHandlerWebTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private ContractService contractService;

    @Test
    void unexpectedError_returnsGeneric500() {
        given(contractService.findAll()).willThrow(new IllegalStateException("secret internal state"));

        assertThat(mvc.get().uri("/api/v1/contract"))
                .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyText()
                .contains("Unexpected error")
                .doesNotContain("secret internal state", "IllegalStateException", "at br.com");
    }

    @Test
    void dataIntegrityViolation_returns409WithoutSql() {
        given(contractService.findAll()).willThrow(
                new DataIntegrityViolationException("insert into punch_list.contract violates fk_x"));

        assertThat(mvc.get().uri("/api/v1/contract"))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyText()
                .doesNotContain("insert into", "punch_list", "fk_x");
    }

    @Test
    void invalidPathVariableType_returns400() {
        assertThat(mvc.get().uri("/api/v1/contract/abc"))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}
