package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.shared.error.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@WebMvcTest(ResponsiblePersonController.class)
class ResponsiblePersonControllerWebTest {

    private static final String URL = "/api/v1/responsible-person";

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private ResponsiblePersonService responsiblePersonService;

    @Test
    void findAllByContract_returnsPage() {
        given(responsiblePersonService.findAllByContract(any(), any())).willReturn(new PageImpl<>(
                List.of(new ResponsiblePersonResponse(1L, "n", "o", "d", null, true, null)),
                PageRequest.of(0, 20), 1));

        assertThat(mvc.get().uri(URL + "/contract/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.content[0].name").isEqualTo("n");
    }

    @Test
    void findById_notFound_returnsProblemDetail() {
        given(responsiblePersonService.findById(7L))
                .willThrow(new ResourceNotFoundException("ResponsiblePerson", 7L));

        assertThat(mvc.get().uri(URL + "/7"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .bodyJson()
                .extractingPath("$.type").isEqualTo("https://punch-list/errors/not-found");
    }

    @Test
    void create_missingRequiredFields_returns400WithFieldErrors() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"n\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[*].field").asArray()
                .contains("idContract", "occupation", "department");
        verifyNoInteractions(responsiblePersonService);
    }

    @Test
    void create_fieldAboveLimit_returns400() {
        // AM-07: sizes are bounded by the column length
        String tooLong = "x".repeat(121);
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idContract\":1,\"name\":\"" + tooLong + "\",\"occupation\":\"o\",\"department\":\"d\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[0].field").isEqualTo("name");
        verifyNoInteractions(responsiblePersonService);
    }

    @Test
    void create_returns201WithLocation() {
        given(responsiblePersonService.create(any())).willReturn(
                new ResponsiblePersonResponse(5L, "n", "o", "d", null, true, null));

        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idContract\":1,\"name\":\"n\",\"occupation\":\"o\",\"department\":\"d\"}"))
                .hasStatus(HttpStatus.CREATED)
                .headers().hasValue("Location", "http://localhost/api/v1/responsible-person/5");
    }
}
