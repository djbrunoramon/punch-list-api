package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.contract.ContractService;
import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;
import br.com.engbr.examples.punchlistapi.shared.error.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResponsiblePersonService {

    static final String RESOURCE = "resource.responsiblePerson";

    private final ResponsiblePersonRepository responsiblePersonRepository;
    private final ContractService contractService;

    public ResponsiblePersonService(ResponsiblePersonRepository responsiblePersonRepository,
                                    ContractService contractService) {
        this.responsiblePersonRepository = responsiblePersonRepository;
        this.contractService = contractService;
    }

    @Transactional(readOnly = true)
    public Page<ResponsiblePersonResponse> findAllByContract(Long contractId, Pageable pageable) {
        return responsiblePersonRepository.findAllByContractId(contractId, pageable)
                .map(ResponsiblePersonMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ResponsiblePersonResponse findById(Long id) {
        return ResponsiblePersonMapper.toResponse(getEntity(id));
    }

    public ResponsiblePersonResponse create(ResponsiblePersonRequest request) {
        Contract contract = contractService.getEntity(request.idContract());
        ResponsiblePerson saved = responsiblePersonRepository.save(
                ResponsiblePersonMapper.toEntity(request, contract));
        return ResponsiblePersonMapper.toResponse(saved);
    }

    public ResponsiblePersonResponse update(Long id, ResponsiblePersonRequest request) {
        ResponsiblePerson person = getEntity(id);
        if (!person.getContract().getId().equals(request.idContract())) {
            // pendencies reference people of their own contract; moving a person would break that invariant
            throw new BusinessRuleException("error.businessRule.responsiblePersonContractImmutable");
        }
        ResponsiblePersonMapper.update(person, request, person.getContract());
        return ResponsiblePersonMapper.toResponse(person);
    }

    /**
     * Returns the managed entity (contract loaded), for features that need to reference a responsible person.
     *
     * @throws ResourceNotFoundException when the responsible person does not exist
     */
    @Transactional(readOnly = true)
    public ResponsiblePerson getEntity(Long id) {
        return responsiblePersonRepository.findWithContractById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }
}
