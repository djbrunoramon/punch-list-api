package br.com.engbr.examples.punchlistapi.shared.i18n;

import br.com.engbr.examples.punchlistapi.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Problem details are localized by Accept-Language (en, pt-BR). The test JVM runs with the pt_BR default locale
 * (see the surefire/failsafe configuration), so English answers prove the JVM locale is not used.
 */
@IntegrationTest
class I18nIT {

    @Autowired
    private MockMvcTester mvc;

    @Test
    void notFound_withoutAcceptLanguage_isEnglish() {
        var result = mvc.get().uri("/api/v1/contract/999").exchange();
        assertThat(result).hasStatus(HttpStatus.NOT_FOUND)
                .headers().hasValue(HttpHeaders.CONTENT_LANGUAGE, "en");
        assertThat(result).bodyJson().extractingPath("$.title").isEqualTo("Resource not found");
        assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Contract 999 not found");
    }

    @Test
    void notFound_inPortuguese() {
        var result = mvc.get().uri("/api/v1/contract/999").header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR").exchange();
        assertThat(result).hasStatus(HttpStatus.NOT_FOUND)
                .headers().hasValue(HttpHeaders.CONTENT_LANGUAGE, "pt-BR");
        assertThat(result).bodyJson().extractingPath("$.title").isEqualTo("Recurso não encontrado");
        assertThat(result).bodyJson().extractingPath("$.detail").isEqualTo("Contrato 999 não encontrado");
        assertThat(result).bodyJson().extractingPath("$.type").isEqualTo("https://punch-list/errors/not-found");
    }

    @Test
    void portugueseVariant_fallsBackToPtBr() {
        assertThat(mvc.get().uri("/api/v1/contract/999").header(HttpHeaders.ACCEPT_LANGUAGE, "pt-PT,pt;q=0.9"))
                .headers().hasValue(HttpHeaders.CONTENT_LANGUAGE, "pt-BR");
    }

    @Test
    void unsupportedOrMaliciousLanguage_fallsBackToEnglish() {
        // AM-I1: any value outside the supported list is ignored
        var result = mvc.get().uri("/api/v1/contract/999")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "xx-<script>alert(1)</script>").exchange();
        assertThat(result).hasStatus(HttpStatus.NOT_FOUND)
                .headers().hasValue(HttpHeaders.CONTENT_LANGUAGE, "en");
        assertThat(result).bodyText().doesNotContain("<script>");
    }

    @Test
    void validationErrors_inPortuguese() {
        var result = mvc.post().uri("/api/v1/contract")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"d\",\"address\":\"a\"}")
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(result).bodyJson().extractingPath("$.title").isEqualTo("Requisição inválida");
        assertThat(result).bodyJson().extractingPath("$.errors[0].message").isEqualTo("não deve estar em branco");
    }

    @Test
    void validationErrors_inEnglish_evenWithPortugueseJvm() {
        assertThat(mvc.post().uri("/api/v1/contract")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"d\",\"address\":\"a\"}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.errors[0].message").isEqualTo("must not be blank");
    }

    @Test
    void businessRule_inPortuguese_withTranslatedFieldNames() {
        assertThat(mvc.post().uri("/api/v1/pendency")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"idContract":1,"areaIdentification":"A","description":"d","priority":"A",
                         "status":"CLOSED","discipline":"Piping","registeredBy":1,"registeredTo":2}
                        """))
                .hasStatus(HttpStatus.UNPROCESSABLE_CONTENT)
                .bodyJson().extractingPath("$.detail")
                .isEqualTo("O status CLOSED exige finalizado por e finalizado em");
    }

    @Test
    void springBuiltInProblem_isTranslated() {
        assertThat(mvc.get().uri("/api/v1/pendency/contract/1/chart/bar/INVALID")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.title").isEqualTo("Requisição inválida");
    }

    @Test
    void malformedBody_isTranslated() {
        var json = assertThat(mvc.post().uri("/api/v1/contract")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{not json"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson();
        json.extractingPath("$.title").isEqualTo("Requisição inválida");
        json.extractingPath("$.detail").isEqualTo("Não foi possível ler o corpo da requisição");
    }

    @Test
    void typeMismatchDetail_isTranslated() {
        assertThat(mvc.get().uri("/api/v1/pendency/contract/1/chart/bar/INVALID")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR"))
                .bodyJson().extractingPath("$.detail").isEqualTo("Valor inválido para 'status'");
    }
}
