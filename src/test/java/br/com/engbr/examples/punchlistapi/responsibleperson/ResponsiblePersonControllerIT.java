package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ResponsiblePersonControllerIT {

    private static final String URL = "/api/v1/responsible-person";

    @Autowired
    private MockMvcTester mvc;

    @Test
    void findAllByContract_returnsDemoPeopleWithContract() {
        var json = assertThat(mvc.get().uri(URL + "/contract/1")).hasStatusOk().bodyJson();
        json.extractingPath("$.page.totalElements").isEqualTo(3);
        json.extractingPath("$.content[0].contract.id").isEqualTo(1);
    }

    @Test
    void findAllByContract_usesPageEnvelope() {
        var json = assertThat(mvc.get().uri(URL + "/contract/1")).hasStatusOk().bodyJson();
        json.extractingPath("$.page.size").isEqualTo(20);
        json.extractingPath("$.page.number").isEqualTo(0);
    }

    @Test
    void findAllByContract_sizeAboveMax_isCappedAt100() {
        // AM-06
        assertThat(mvc.get().uri(URL + "/contract/1").param("size", "10000"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.page.size").isEqualTo(100);
    }

    @Test
    void findById_formatsCreatedAtWithoutSeconds() {
        assertThat(mvc.get().uri(URL + "/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.createdAt").asString().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
    }

    @Test
    void findById_unknown_returns404() {
        assertThat(mvc.get().uri(URL + "/999")).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void create_persistsAndReturnsLocation() {
        var result = mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":1,"name":"Ana","occupation":"Inspector","department":"QA"}
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).headers().hasHeaderSatisfying("Location",
                values -> assertThat(values.getFirst()).matches(".*/api/v1/responsible-person/\\d+"));
        assertThat(result).bodyJson().extractingPath("$.contract.id").isEqualTo(1);
    }

    @Test
    void create_unknownContract_returns404() {
        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":999,"name":"Ana","occupation":"Inspector","department":"QA"}
                        """))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void create_ignoresServerManagedFields() {
        // AM-05: id and active are not client-assignable
        var result = mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"id":999,"active":false,"createdAt":"2000-01-01T00:00",
                         "idContract":1,"name":"Ana","occupation":"Inspector","department":"QA"}
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
                        {"idContract":1,"name":"Renamed","occupation":"Engineer","department":"TI"}
                        """))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.name").isEqualTo("Renamed");
    }

    @Test
    void update_unknown_returns404() {
        assertThat(mvc.put().uri(URL + "/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":1,"name":"N","occupation":"o","department":"d"}
                        """))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void update_changingContract_returns422AndKeepsContract() {
        String location = mvc.post().uri("/api/v1/contract")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberContract\":\"OTHER\",\"description\":\"d\",\"address\":\"a\"}")
                .exchange().getResponse().getHeader("Location");
        String otherContractId = location.substring(location.lastIndexOf('/') + 1);

        assertThat(mvc.put().uri(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idContract\":" + otherContractId
                        + ",\"name\":\"Bruno Machado\",\"occupation\":\"o\",\"department\":\"d\"}"))
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .bodyJson()
                .extractingPath("$.type").isEqualTo("https://punch-list/errors/business-rule");

        assertThat(mvc.get().uri(URL + "/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.contract.id").isEqualTo(1);
    }
}
