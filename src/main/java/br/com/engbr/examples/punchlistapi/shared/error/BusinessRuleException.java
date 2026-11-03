package br.com.engbr.examples.punchlistapi.shared.error;

/**
 * Thrown when a request is well-formed but violates a domain rule. Mapped to HTTP 422.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
