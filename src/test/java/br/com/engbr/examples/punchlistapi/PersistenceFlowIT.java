package br.com.engbr.examples.punchlistapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end flow WITHOUT a test transaction: every request commits and every read runs in a fresh
 * persistence context, as in production (open-in-view=false). Uses its own contract, so the demo data
 * asserted by the other integration tests is untouched.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class PersistenceFlowIT {

    @Autowired
    private MockMvcTester mvc;

    @Test
    void createAndUpdateThroughTheApi_arePersistedAndReadable() {
        long contractId = idFromLocation(mvc.post().uri("/api/v1/contract")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"numberContract":"FLOW-1","description":"Flow","address":"Recife-PE-BR"}
                        """));
        long registeredBy = createPerson(contractId, "Ana");
        long registeredTo = createPerson(contractId, "Rui");

        long pendencyId = idFromLocation(mvc.post().uri("/api/v1/pendency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pendency(contractId, registeredBy, registeredTo, "OPEN", "")));

        assertThat(mvc.put().uri("/api/v1/pendency/" + pendencyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(pendency(contractId, registeredBy, registeredTo, "CLOSED",
                        ",\"finishedBy\":" + registeredTo + ",\"finishedAt\":\"2026-11-03T10:00\"")))
                .hasStatusOk();

        // fresh reads: relations are loaded inside the service transaction (no lazy loading in the view)
        var pendency = assertThat(mvc.get().uri("/api/v1/pendency/" + pendencyId)).hasStatusOk().bodyJson();
        pendency.extractingPath("$.status").isEqualTo("CLOSED");
        pendency.extractingPath("$.finishedBy.name").isEqualTo("Rui");
        pendency.extractingPath("$.finishedAt").isEqualTo("2026-11-03T10:00");
        pendency.extractingPath("$.contract.numberContract").isEqualTo("FLOW-1");

        var page = assertThat(mvc.get().uri("/api/v1/pendency/contract/" + contractId)).hasStatusOk().bodyJson();
        page.extractingPath("$.page.totalElements").isEqualTo(1);
        page.extractingPath("$.content[0].registeredBy.name").isEqualTo("Ana");

        assertThat(mvc.get().uri("/api/v1/responsible-person/contract/" + contractId))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.content[0].contract.id").isEqualTo((int) contractId);

        var pie = assertThat(mvc.get().uri("/api/v1/pendency/contract/" + contractId + "/chart/pie"))
                .hasStatusOk().bodyJson();
        pie.extractingPath("$[?(@.label=='CLOSED')].quantity").asArray().containsExactly(1);
    }

    private long createPerson(long contractId, String name) {
        return idFromLocation(mvc.post().uri("/api/v1/responsible-person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":%d,"name":"%s","occupation":"Engineer","department":"C&M"}
                        """.formatted(contractId, name)));
    }

    private static String pendency(long contractId, long by, long to, String status, String extra) {
        return """
                {"idContract":%d,"areaIdentification":"BOILER","description":"Check valve","priority":"A",
                 "status":"%s","discipline":"Piping","registeredBy":%d,"registeredTo":%d%s}
                """.formatted(contractId, status, by, to, extra);
    }

    private long idFromLocation(MockMvcTester.MockMvcRequestBuilder request) {
        var result = request.exchange();
        assertThat(result).hasStatus(HttpStatus.CREATED);
        String location = result.getResponse().getHeader("Location");
        return Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
    }
}
