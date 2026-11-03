package br.com.engbr.examples.punchlistapi.shared.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Allowed browser origins, bound from {@code app.cors.allowed-origins} / {@code APP_CORS_ALLOWED_ORIGINS}.
 */
@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(List<String> allowedOrigins) {

    public CorsProperties {
        allowedOrigins = allowedOrigins == null ? List.of() : List.copyOf(allowedOrigins);
    }
}
