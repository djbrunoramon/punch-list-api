package br.com.engbr.examples.punchlistapi.contract;

/**
 * Explicit mapping between the API records and the entity. Only client-editable fields are copied.
 */
public final class ContractMapper {

    private ContractMapper() {
    }

    public static Contract toEntity(ContractRequest request) {
        Contract contract = new Contract();
        update(contract, request);
        return contract;
    }

    public static void update(Contract contract, ContractRequest request) {
        contract.setNumberContract(request.numberContract());
        contract.setDescription(request.description());
        contract.setAddress(request.address());
        contract.setStartAt(request.startAt());
        contract.setScheduledTo(request.scheduledTo());
        contract.setEstimatedAt(request.estimatedAt());
    }

    public static ContractResponse toResponse(Contract contract) {
        return new ContractResponse(
                contract.getId(),
                contract.getNumberContract(),
                contract.getDescription(),
                contract.getAddress(),
                contract.getStartAt(),
                contract.getScheduledTo(),
                contract.isActive(),
                contract.getInactiveAt(),
                contract.getEstimatedAt());
    }
}
