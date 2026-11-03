package br.com.engbr.examples.punchlistapi.shared.error;

/**
 * Thrown when a resource addressed by id does not exist. Mapped to HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Object id) {
        super("%s %s not found".formatted(resource, id));
    }
}
