package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;

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
                    "registeredBy and registeredTo");
            case DISAPPROVED -> require(p, p.getDisapprovedBy() != null && p.getDisapprovedAt() != null,
                    "disapprovedBy and disapprovedAt");
            case CANCELED -> require(p, p.getCanceledBy() != null && p.getCanceledAt() != null,
                    "canceledBy and canceledAt");
            case CLOSED -> require(p, p.getFinishedBy() != null && p.getFinishedAt() != null,
                    "finishedBy and finishedAt");
        }
    }

    private static void require(Pendency p, boolean satisfied, String fields) {
        if (!satisfied) {
            throw new BusinessRuleException("Status %s requires %s".formatted(p.getStatus(), fields));
        }
    }
}
