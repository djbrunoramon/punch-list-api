package br.com.engbr.examples.punchlistapi.contract;

import br.com.engbr.examples.punchlistapi.shared.error.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@WebMvcTest(ContractController.class)
class ContractControllerWebTest {

    private static final String URL = "/api/v1/contract";

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private ContractService contractService;

    @Test
    void findAll_returnsList() {
        given(contractService.findAll()).willReturn(List.of(
                new ContractResponse(1L, "N-1", "d", "a", null, null, true, null, null)));

        assertThat(mvc.get().uri(URL))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].numberContract").isEqualTo("N-1");
    }

    @Test
    void findById_notFound_returnsProblemDetail() {
        given(contractService.findById(7L)).willThrow(new ResourceNotFoundException("resource.contract", 7L));

        assertThat(mvc.get().uri(URL + "/7"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .bodyJson()
                .satisfies(json -> {
                    assertThat(json).extractingPath("$.type").isEqualTo("https://punch-list/errors/not-found");
                    assertThat(json).extractingPath("$.status").isEqualTo(404);
                });
    }

    @Test
    void create_missingRequiredFields_returns400WithFieldErrors() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"d\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[*].field").asArray()
                .contains("numberContract", "address");
        verifyNoInteractions(contractService);
    }

    @Test
    void create_fieldAboveLimit_returns400() {
        // AM-07: sizes are bounded by the column length
        String tooLong = "x".repeat(51);
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberContract\":\"" + tooLong + "\",\"description\":\"d\",\"address\":\"a\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[0].field").isEqualTo("numberContract");
    }

    @Test
    void create_negativeEstimate_returns400() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberContract\":\"n\",\"description\":\"d\",\"address\":\"a\",\"estimatedAt\":-1}"))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_malformedJson_returns400WithoutParserDetails() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{not json"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyText().doesNotContain("JsonParseException", "tools.jackson", "com.fasterxml");
    }

    @Test
    void create_returns201() {
        given(contractService.create(any())).willReturn(
                new ContractResponse(5L, "n", "d", "a", null, null, true, null, null));

        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberContract\":\"n\",\"description\":\"d\",\"address\":\"a\"}"))
                .hasStatus(HttpStatus.CREATED)
                .headers().hasValue("Location", "http://localhost/api/v1/contract/5");
    }
}
