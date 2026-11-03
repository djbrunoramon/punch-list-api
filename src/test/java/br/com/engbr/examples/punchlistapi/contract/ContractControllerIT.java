package br.com.engbr.examples.punchlistapi.contract;

import br.com.engbr.examples.punchlistapi.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ContractControllerIT {

    private static final String URL = "/api/v1/contract";

    @Autowired
    private MockMvcTester mvc;

    @Test
    void findAll_returnsDemoContract() {
        assertThat(mvc.get().uri(URL))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].numberContract").isEqualTo("BR2022-001");
    }

    @Test
    void findById_formatsDatesWithoutSeconds() {
        assertThat(mvc.get().uri(URL + "/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.startAt").asString().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
    }

    @Test
    void findById_unknown_returns404Problem() {
        assertThat(mvc.get().uri(URL + "/999"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .bodyJson()
                .extractingPath("$.detail").isEqualTo("Contract 999 not found");
    }

    @Test
    void create_persistsAndReturnsLocation() {
        var result = mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"numberContract":"BR2026-010","description":"Wind farm","address":"Natal-RN-BR",
                         "startAt":"2026-12-01T08:00","estimatedAt":1500.50}
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).headers().hasHeaderSatisfying("Location",
                values -> assertThat(values.getFirst()).matches(".*/api/v1/contract/\\d+"));
        assertThat(result).bodyJson().extractingPath("$.startAt").isEqualTo("2026-12-01T08:00");
    }

    @Test
    void create_ignoresServerManagedFields() {
        // AM-05: id, active and createdAt are not client-assignable
        var result = mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"id":999,"active":false,"createdAt":"2000-01-01T00:00",
                         "numberContract":"X-1","description":"d","address":"a"}
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.id").isNotEqualTo(999);
        assertThat(result).bodyJson().extractingPath("$.active").isEqualTo(true);
    }

    @Test
    void update_changesEditableFields() {
        assertThat(mvc.put().uri(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"numberContract":"BR2022-001","description":"Updated","address":"Manaus-AM-BR"}
                        """))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.description").isEqualTo("Updated");
    }

    @Test
    void update_unknown_returns404() {
        assertThat(mvc.put().uri(URL + "/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"numberContract":"N","description":"d","address":"a"}
                        """))
                .hasStatus(HttpStatus.NOT_FOUND);
    }
}
