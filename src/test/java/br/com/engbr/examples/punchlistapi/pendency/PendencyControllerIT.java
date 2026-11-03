package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PendencyControllerIT {

    private static final String URL = "/api/v1/pendency";

    @Autowired
    private MockMvcTester mvc;

    private static String body(String status, String extra) {
        return """
                {"idContract":1,"areaIdentification":"GAS TURBINE","description":"d","priority":"A",
                 "status":"%s","discipline":"Piping","registeredBy":1,"registeredTo":2%s}
                """.formatted(status, extra);
    }

    @Test
    void findAllByContract_returnsDemoPendencies() {
        assertThat(mvc.get().uri(URL + "/contract/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.page.totalElements").isEqualTo(3);
    }

    @Test
    void findById_embedsResponsiblePeopleWithoutNestedContract() {
        var json = assertThat(mvc.get().uri(URL + "/1")).hasStatusOk().bodyJson();
        json.extractingPath("$.contract.id").isEqualTo(1);
        json.extractingPath("$.registeredBy.name").isEqualTo("William Souza");
        json.doesNotHavePath("$.registeredBy.contract");
        json.extractingPath("$.createdAt").asString().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
    }

    @Test
    void findById_unknown_returns404() {
        assertThat(mvc.get().uri(URL + "/999")).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void chartPie_returnsAllFourStatuses() {
        var json = assertThat(mvc.get().uri(URL + "/contract/1/chart/pie")).hasStatusOk().bodyJson();
        json.extractingPath("$.length()").isEqualTo(4);
        json.extractingPath("$[0].label").isEqualTo("OPEN");
        json.extractingPath("$[0].quantity").isEqualTo(3);
        json.extractingPath("$[1].label").isEqualTo("CLOSED");
        json.extractingPath("$[1].quantity").isEqualTo(0);
    }

    @Test
    void chartBar_groupsByPriority() {
        var json = assertThat(mvc.get().uri(URL + "/contract/1/chart/bar/OPEN")).hasStatusOk().bodyJson();
        json.extractingPath("$[0].priority").isEqualTo("A");
        json.extractingPath("$[0].quantity").isEqualTo(1);
        json.extractingPath("$[1].priority").isEqualTo("B");
        json.extractingPath("$[1].quantity").isEqualTo(2);
    }

    @Test
    void chartBar_invalidStatus_returns400() {
        assertThat(mvc.get().uri(URL + "/contract/1/chart/bar/INVALID")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_open_returns201WithLocation() {
        var result = mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON).content(body("OPEN", "")).exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).headers().hasHeaderSatisfying("Location",
                values -> assertThat(values.getFirst()).matches(".*/api/v1/pendency/\\d+"));
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo("OPEN");
    }

    @Test
    void create_ignoresServerManagedFields() {
        // AM-05
        var result = mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON)
                .content(body("OPEN", ",\"id\":999,\"createdAt\":\"2000-01-01T00:00\""))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.id").isNotEqualTo(999);
        assertThat(result).bodyJson().extractingPath("$.createdAt").asString().doesNotStartWith("2000");
    }

    @Test
    void create_closedWithoutFinishedBy_returns422() {
        assertThat(mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON).content(body("CLOSED", "")))
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .bodyJson()
                .extractingPath("$.detail").isEqualTo("Status CLOSED requires finishedBy and finishedAt");
    }

    @Test
    void create_unknownResponsiblePerson_returns404() {
        assertThat(mvc.post().uri(URL).contentType(MediaType.APPLICATION_JSON)
                .content(body("OPEN", "").replace("\"registeredBy\":1", "\"registeredBy\":999")))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void update_toClosed_returns200() {
        assertThat(mvc.put().uri(URL + "/1").contentType(MediaType.APPLICATION_JSON)
                .content(body("CLOSED", ",\"finishedBy\":3,\"finishedAt\":\"2026-11-01T10:00\"")))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.status").isEqualTo("CLOSED");
    }

    @Test
    void update_unknown_returns404() {
        assertThat(mvc.put().uri(URL + "/999").contentType(MediaType.APPLICATION_JSON).content(body("OPEN", "")))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void create_responsiblePersonFromAnotherContract_returns422() {
        String otherContract = mvc.post().uri("/api/v1/contract")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberContract\":\"OTHER\",\"description\":\"d\",\"address\":\"a\"}")
                .exchange().getResponse().getHeader("Location");
        String contractId = otherContract.substring(otherContract.lastIndexOf('/') + 1);
        String otherPerson = mvc.post().uri("/api/v1/responsible-person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idContract\":" + contractId
                        + ",\"name\":\"Outsider\",\"occupation\":\"o\",\"department\":\"d\"}")
                .exchange().getResponse().getHeader("Location");
        String personId = otherPerson.substring(otherPerson.lastIndexOf('/') + 1);

        assertThat(mvc.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":1,"areaIdentification":"A","description":"d","priority":"A",
                         "status":"OPEN","discipline":"Piping","registeredBy":1,"registeredTo":%s}
                        """.formatted(personId)))
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .bodyJson()
                .extractingPath("$.type").isEqualTo("https://punch-list/errors/business-rule");
    }
}
