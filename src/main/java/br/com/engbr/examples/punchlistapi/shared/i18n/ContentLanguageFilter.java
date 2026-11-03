package br.com.engbr.examples.punchlistapi.shared.i18n;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;

/**
 * Tells the client which language the response uses ({@code Content-Language}), for every response including
 * errors raised before a controller is reached.
 */
@Component
public class ContentLanguageFilter extends OncePerRequestFilter {

    private final LocaleResolver localeResolver;

    public ContentLanguageFilter(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        response.setHeader(HttpHeaders.CONTENT_LANGUAGE, localeResolver.resolveLocale(request).toLanguageTag());
        chain.doFilter(request, response);
    }
}
