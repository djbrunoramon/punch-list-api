package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.contract.ContractMapper;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePersonMapper;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePersonSummary;

/**
 * Explicit mapping between the API records and the entity. Only client-editable fields are copied;
 * referenced entities are resolved by the service and passed in.
 */
public final class PendencyMapper {

    private PendencyMapper() {
    }

    /** Resolved references of a pendency request. */
    public record References(Contract contract, ResponsiblePerson registeredBy, ResponsiblePerson registeredTo,
                             ResponsiblePerson finishedBy, ResponsiblePerson disapprovedBy,
                             ResponsiblePerson canceledBy) {
    }

    public static Pendency toEntity(PendencyRequest request, References refs) {
        Pendency pendency = new Pendency();
        update(pendency, request, refs);
        return pendency;
    }

    public static void update(Pendency pendency, PendencyRequest request, References refs) {
        pendency.setContract(refs.contract());
        pendency.setAreaIdentification(request.areaIdentification());
        pendency.setTag(request.tag());
        pendency.setDescription(request.description());
        pendency.setPriority(request.priority());
        pendency.setDiscipline(request.discipline());
        pendency.setStatus(request.status());
        pendency.setRegisteredBy(refs.registeredBy());
        pendency.setRegisteredTo(refs.registeredTo());
        pendency.setExpectedIn(request.expectedIn());
        pendency.setFinishedBy(refs.finishedBy());
        pendency.setFinishedAt(request.finishedAt());
        pendency.setDisapprovedBy(refs.disapprovedBy());
        pendency.setDisapprovedAt(request.disapprovedAt());
        pendency.setCanceledBy(refs.canceledBy());
        pendency.setCanceledAt(request.canceledAt());
    }

    public static PendencyResponse toResponse(Pendency p) {
        return new PendencyResponse(
                p.getId(),
                p.getAreaIdentification(),
                p.getTag(),
                p.getDescription(),
                p.getPriority(),
                p.getDiscipline(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getModifiedIn(),
                p.getExpectedIn(),
                p.getFinishedAt(),
                p.getDisapprovedAt(),
                p.getCanceledAt(),
                ContractMapper.toResponse(p.getContract()),
                summary(p.getRegisteredBy()),
                summary(p.getRegisteredTo()),
                summary(p.getFinishedBy()),
                summary(p.getDisapprovedBy()),
                summary(p.getCanceledBy()));
    }

    private static ResponsiblePersonSummary summary(ResponsiblePerson person) {
        return person == null ? null : ResponsiblePersonMapper.toSummary(person);
    }
}
