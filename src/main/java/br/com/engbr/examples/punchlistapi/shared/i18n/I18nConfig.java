package br.com.engbr.examples.punchlistapi.shared.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Supported languages, resolved from {@code Accept-Language}. Anything else falls back to English, regardless of
 * the JVM default locale.
 */
@Configuration(proxyBeanMethods = false)
public class I18nConfig {

    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    public static final List<Locale> SUPPORTED_LOCALES = List.of(Locale.ENGLISH, Locale.forLanguageTag("pt-BR"));

    @Bean
    public LocaleResolver localeResolver() {
        return new LanguageLocaleResolver(SUPPORTED_LOCALES, DEFAULT_LOCALE);
    }
}
