package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PendencyStatusValidatorTest {

    private static Pendency valid(PendencyStatus status) {
        Pendency p = new Pendency();
        p.setStatus(status);
        p.setRegisteredBy(new ResponsiblePerson());
        p.setRegisteredTo(new ResponsiblePerson());
        switch (status) {
            case CLOSED -> {
                p.setFinishedBy(new ResponsiblePerson());
                p.setFinishedAt(LocalDateTime.now());
            }
            case DISAPPROVED -> {
                p.setDisapprovedBy(new ResponsiblePerson());
                p.setDisapprovedAt(LocalDateTime.now());
            }
            case CANCELED -> {
                p.setCanceledBy(new ResponsiblePerson());
                p.setCanceledAt(LocalDateTime.now());
            }
            case OPEN -> {
            }
        }
        return p;
    }

    @ParameterizedTest
    @EnumSource(PendencyStatus.class)
    void validPendency_passes(PendencyStatus status) {
        assertThatCode(() -> PendencyStatusValidator.validate(valid(status))).doesNotThrowAnyException();
    }

    @Test
    void open_withoutRegisteredBy_fails() {
        Pendency p = valid(PendencyStatus.OPEN);
        p.setRegisteredBy(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Status OPEN requires registeredBy and registeredTo");
    }

    @Test
    void open_withoutRegisteredTo_fails() {
        Pendency p = valid(PendencyStatus.OPEN);
        p.setRegisteredTo(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p)).isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void closed_withoutFinishedBy_fails() {
        Pendency p = valid(PendencyStatus.CLOSED);
        p.setFinishedBy(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Status CLOSED requires finishedBy and finishedAt");
    }

    @Test
    void closed_withoutFinishedAt_fails() {
        Pendency p = valid(PendencyStatus.CLOSED);
        p.setFinishedAt(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p)).isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void disapproved_withoutDisapprovedBy_fails() {
        Pendency p = valid(PendencyStatus.DISAPPROVED);
        p.setDisapprovedBy(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Status DISAPPROVED requires disapprovedBy and disapprovedAt");
    }

    @Test
    void disapproved_withoutDisapprovedAt_fails() {
        Pendency p = valid(PendencyStatus.DISAPPROVED);
        p.setDisapprovedAt(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p)).isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void canceled_withoutCanceledBy_fails() {
        Pendency p = valid(PendencyStatus.CANCELED);
        p.setCanceledBy(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Status CANCELED requires canceledBy and canceledAt");
    }

    @Test
    void canceled_withoutCanceledAt_fails() {
        Pendency p = valid(PendencyStatus.CANCELED);
        p.setCanceledAt(null);
        assertThatThrownBy(() -> PendencyStatusValidator.validate(p)).isInstanceOf(BusinessRuleException.class);
    }
}
