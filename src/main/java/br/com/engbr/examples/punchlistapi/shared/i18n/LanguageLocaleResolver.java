package br.com.engbr.examples.punchlistapi.shared.i18n;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Picks the first supported language of {@code Accept-Language} (in the client's preference order), matching by
 * language only, so {@code pt}, {@code pt-PT} or {@code pt-BR} all resolve to {@code pt-BR}. Unsupported or
 * malformed values fall back to the default locale; the JVM locale is never used.
 */
public class LanguageLocaleResolver implements LocaleResolver {

    private final List<Locale> supported;
    private final Locale defaultLocale;

    public LanguageLocaleResolver(List<Locale> supported, Locale defaultLocale) {
        this.supported = List.copyOf(supported);
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (request.getHeader(HttpHeaders.ACCEPT_LANGUAGE) == null) {
            return defaultLocale;
        }
        for (Locale requested : Collections.list(request.getLocales())) {
            for (Locale candidate : supported) {
                if (candidate.getLanguage().equals(requested.getLanguage())) {
                    return candidate;
                }
            }
        }
        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("The locale comes from the Accept-Language header");
    }
}
