package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;
import org.springframework.context.support.DefaultMessageSourceResolvable;

/**
 * Status-dependent business rules for a fully assembled pendency.
 */
public final class PendencyStatusValidator {

    private PendencyStatusValidator() {
    }

    /**
     * @throws BusinessRuleException when the fields required by the pendency status are missing
     */
    public static void validate(Pendency p) {
        switch (p.getStatus()) {
            case OPEN -> require(p, p.getRegisteredBy() != null && p.getRegisteredTo() != null,
                    "registeredBy", "registeredTo");
            case DISAPPROVED -> require(p, p.getDisapprovedBy() != null && p.getDisapprovedAt() != null,
                    "disapprovedBy", "disapprovedAt");
            case CANCELED -> require(p, p.getCanceledBy() != null && p.getCanceledAt() != null,
                    "canceledBy", "canceledAt");
            case CLOSED -> require(p, p.getFinishedBy() != null && p.getFinishedAt() != null,
                    "finishedBy", "finishedAt");
        }
    }

    /** Message {@code error.businessRule.pendencyStatusRequiredFields}: {0} status, {1} and {2} field names. */
    static final String MESSAGE_CODE = "error.businessRule.pendencyStatusRequiredFields";

    private static void require(Pendency p, boolean satisfied, String firstField, String secondField) {
        if (!satisfied) {
            throw new BusinessRuleException(MESSAGE_CODE, p.getStatus().name(),
                    new DefaultMessageSourceResolvable("field." + firstField),
                    new DefaultMessageSourceResolvable("field." + secondField));
        }
    }
}
