package br.com.engbr.examples.punchlistapi.contract;

import br.com.engbr.examples.punchlistapi.shared.error.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractService {

    static final String RESOURCE = "resource.contract";

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public List<ContractResponse> findAll() {
        return contractRepository.findAll(Sort.by("id")).stream()
                .map(ContractMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContractResponse findById(Long id) {
        return ContractMapper.toResponse(getEntity(id));
    }

    public ContractResponse create(ContractRequest request) {
        Contract saved = contractRepository.save(ContractMapper.toEntity(request));
        return ContractMapper.toResponse(saved);
    }

    public ContractResponse update(Long id, ContractRequest request) {
        Contract contract = getEntity(id);
        ContractMapper.update(contract, request);
        return ContractMapper.toResponse(contract);
    }

    /**
     * Returns the managed entity, for features that need to reference a contract.
     *
     * @throws ResourceNotFoundException when the contract does not exist
     */
    @Transactional(readOnly = true)
    public Contract getEntity(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }
}
