package br.com.engbr.examples.punchlistapi.shared.error;

import org.springframework.context.MessageSourceResolvable;

import java.util.Arrays;

/**
 * Thrown when a request is well-formed but violates a domain rule. Mapped to HTTP 422.
 * Carries a message code ({@code error.businessRule.*}) and its arguments; arguments may themselves be
 * {@link MessageSourceResolvable} (e.g. translated field names).
 */
public class BusinessRuleException extends RuntimeException implements MessageSourceResolvable {

    private final String messageCode;
    private final transient Object[] arguments;

    public BusinessRuleException(String messageCode, Object... arguments) {
        super(messageCode + " " + Arrays.toString(arguments));
        this.messageCode = messageCode;
        this.arguments = arguments.clone();
    }

    @Override
    public String[] getCodes() {
        return new String[]{messageCode};
    }

    @Override
    public Object[] getArguments() {
        return arguments.clone();
    }
}
