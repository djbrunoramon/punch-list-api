package br.com.engbr.examples.punchlistapi.shared.i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every language file has exactly the keys of the base (en) file, with no blank values (I18N-API-02).
 */
class MessagesParityTest {

    @Test
    void portugueseHasTheSameKeysAsTheBaseFile() throws IOException {
        Properties base = load("/messages.properties");
        Properties pt = load("/messages_pt_BR.properties");

        assertThat(pt.stringPropertyNames()).containsExactlyInAnyOrderElementsOf(base.stringPropertyNames());
        assertThat(base.stringPropertyNames()).isNotEmpty();
        base.stringPropertyNames().forEach(key -> assertThat(base.getProperty(key)).as(key).isNotBlank());
        pt.stringPropertyNames().forEach(key -> assertThat(pt.getProperty(key)).as(key).isNotBlank());
    }

    private static Properties load(String path) throws IOException {
        try (InputStream in = MessagesParityTest.class.getResourceAsStream(path)) {
            assertThat(in).as(path).isNotNull();
            Properties properties = new Properties();
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            return properties;
        }
    }
}
