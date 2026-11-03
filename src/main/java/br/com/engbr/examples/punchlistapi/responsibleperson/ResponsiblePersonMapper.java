package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.contract.ContractMapper;

/**
 * Explicit mapping between the API records and the entity. Only client-editable fields are copied.
 */
public final class ResponsiblePersonMapper {

    private ResponsiblePersonMapper() {
    }

    public static ResponsiblePerson toEntity(ResponsiblePersonRequest request, Contract contract) {
        ResponsiblePerson person = new ResponsiblePerson();
        update(person, request, contract);
        return person;
    }

    public static void update(ResponsiblePerson person, ResponsiblePersonRequest request, Contract contract) {
        person.setName(request.name());
        person.setOccupation(request.occupation());
        person.setDepartment(request.department());
        person.setContract(contract);
    }

    public static ResponsiblePersonResponse toResponse(ResponsiblePerson person) {
        return new ResponsiblePersonResponse(
                person.getId(),
                person.getName(),
                person.getOccupation(),
                person.getDepartment(),
                person.getCreatedAt(),
                person.isActive(),
                ContractMapper.toResponse(person.getContract()));
    }

    public static ResponsiblePersonSummary toSummary(ResponsiblePerson person) {
        return new ResponsiblePersonSummary(
                person.getId(),
                person.getName(),
                person.getOccupation(),
                person.getDepartment(),
                person.getCreatedAt(),
                person.isActive());
    }
}
