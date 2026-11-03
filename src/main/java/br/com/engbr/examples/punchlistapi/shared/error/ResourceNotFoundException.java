package br.com.engbr.examples.punchlistapi.shared.error;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;

/**
 * Thrown when a resource addressed by id does not exist. Mapped to HTTP 404.
 * The message is resolved from {@code error.notFound.<resource>} (grammatically specific, e.g. gender in
 * Portuguese) or, if absent, from the generic {@code error.notFound.resource} ({0} = resource name, {1} = id).
 */
public class ResourceNotFoundException extends RuntimeException implements MessageSourceResolvable {

    public static final String MESSAGE_CODE = "error.notFound.resource";

    private final String specificCode;
    private final transient Object[] arguments;

    /**
     * @param resourceCode message code of the resource name, e.g. {@code resource.contract}
     */
    public ResourceNotFoundException(String resourceCode, Object id) {
        super("%s %s not found".formatted(resourceCode, id));
        // ids as text: MessageFormat would otherwise group digits by locale (1,000 / 1.000)
        this.specificCode = "error.notFound." + resourceCode.substring(resourceCode.lastIndexOf('.') + 1);
        this.arguments = new Object[]{
                new DefaultMessageSourceResolvable(new String[]{resourceCode}, resourceCode), String.valueOf(id)};
    }

    @Override
    public String[] getCodes() {
        return new String[]{specificCode, MESSAGE_CODE};
    }

    @Override
    public Object[] getArguments() {
        return arguments.clone();
    }
}
