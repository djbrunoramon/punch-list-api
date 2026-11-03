package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByPriority;
import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;
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

@WebMvcTest(PendencyController.class)
class PendencyControllerWebTest {

    private static final String URL = "/api/v1/pendency";
    private static final String VALID_BODY = """
            {"idContract":1,"areaIdentification":"A","description":"d","priority":"A","status":"OPEN",
             "discipline":"Piping","registeredBy":1,"registeredTo":2}
            """;

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private PendencyService pendencyService;

    private static PendencyResponse response(Long id) {
        return new PendencyResponse(id, "A", null, "d", "A", "Piping", PendencyStatus.OPEN,
                null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @Test
    void create_missingRequiredFields_returns400WithFieldErrors() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"d\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[*].field").asArray()
                .contains("idContract", "status", "registeredBy", "registeredTo", "areaIdentification");
        verifyNoInteractions(pendencyService);
    }

    @Test
    void create_fieldAboveLimit_returns400() {
        // AM-07: sizes are bounded by the column length
        String tooLong = "x".repeat(2001);
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_BODY.replace("\"description\":\"d\"", "\"description\":\"" + tooLong + "\"")))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[0].field").isEqualTo("description");
        verifyNoInteractions(pendencyService);
    }

    @Test
    void create_businessRuleViolation_returns422() {
        given(pendencyService.create(any())).willThrow(
                new BusinessRuleException("Status CLOSED requires finishedBy and finishedAt"));

        assertThat(mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .hasContentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .bodyJson()
                .satisfies(json -> {
                    assertThat(json).extractingPath("$.type").isEqualTo("https://punch-list/errors/business-rule");
                    assertThat(json).extractingPath("$.detail")
                            .isEqualTo("Status CLOSED requires finishedBy and finishedAt");
                });
    }

    @Test
    void findById_notFound_returns404Problem() {
        given(pendencyService.findById(7L)).willThrow(new ResourceNotFoundException("Pendency", 7L));

        assertThat(mvc.get().uri(URL + "/7"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .bodyJson()
                .extractingPath("$.type").isEqualTo("https://punch-list/errors/not-found");
    }

    @Test
    void create_returns201WithLocation() {
        given(pendencyService.create(any())).willReturn(response(5L));

        assertThat(mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
                .hasStatus(HttpStatus.CREATED)
                .headers().hasValue("Location", "http://localhost/api/v1/pendency/5");
    }

    @Test
    void chartBar_returnsRows() {
        given(pendencyService.chartByPriority(1L, PendencyStatus.OPEN))
                .willReturn(List.of(new PendencyByPriority("A", 2L)));

        assertThat(mvc.get().uri(URL + "/contract/1/chart/bar/OPEN"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].priority").isEqualTo("A");
    }

    @Test
    void chartBar_invalidStatus_returns400() {
        assertThat(mvc.get().uri(URL + "/contract/1/chart/bar/INVALID"))
                .hasStatus(HttpStatus.BAD_REQUEST);
        verifyNoInteractions(pendencyService);
    }
}
