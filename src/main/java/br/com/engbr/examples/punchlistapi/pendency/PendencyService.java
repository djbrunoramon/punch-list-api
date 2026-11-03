package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByPriority;
import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByStatus;
import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.contract.ContractService;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePersonService;
import br.com.engbr.examples.punchlistapi.shared.error.BusinessRuleException;
import br.com.engbr.examples.punchlistapi.shared.error.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PendencyService {

    static final String RESOURCE = "Pendency";

    private final PendencyRepository pendencyRepository;
    private final ContractService contractService;
    private final ResponsiblePersonService responsiblePersonService;

    public PendencyService(PendencyRepository pendencyRepository, ContractService contractService,
                           ResponsiblePersonService responsiblePersonService) {
        this.pendencyRepository = pendencyRepository;
        this.contractService = contractService;
        this.responsiblePersonService = responsiblePersonService;
    }

    @Transactional(readOnly = true)
    public Page<PendencyResponse> findAllByContract(Long contractId, Pageable pageable) {
        return pendencyRepository.findAllByContractId(contractId, pageable).map(PendencyMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PendencyResponse findById(Long id) {
        return PendencyMapper.toResponse(getEntity(id));
    }

    public PendencyResponse create(PendencyRequest request) {
        Pendency pendency = PendencyMapper.toEntity(request, resolve(request));
        PendencyStatusValidator.validate(pendency);
        return PendencyMapper.toResponse(pendencyRepository.save(pendency));
    }

    public PendencyResponse update(Long id, PendencyRequest request) {
        Pendency pendency = getEntity(id);
        PendencyMapper.update(pendency, request, resolve(request));
        PendencyStatusValidator.validate(pendency);
        // flush so that @UpdateTimestamp (modifiedIn) is reflected in the response
        return PendencyMapper.toResponse(pendencyRepository.saveAndFlush(pendency));
    }

    /** Always returns the four statuses, in enum order. */
    @Transactional(readOnly = true)
    public List<PendencyByStatus> chartByStatus(Long contractId) {
        return Arrays.stream(PendencyStatus.values())
                .map(status -> new PendencyByStatus(status,
                        pendencyRepository.countByContractIdAndStatus(contractId, status)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PendencyByPriority> chartByPriority(Long contractId, PendencyStatus status) {
        return pendencyRepository.countByPriority(contractId, status);
    }

    private Pendency getEntity(Long id) {
        return pendencyRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    private PendencyMapper.References resolve(PendencyRequest request) {
        Contract contract = contractService.getEntity(request.idContract());
        return new PendencyMapper.References(
                contract,
                person(request.registeredBy(), contract),
                person(request.registeredTo(), contract),
                person(request.finishedBy(), contract),
                person(request.disapprovedBy(), contract),
                person(request.canceledBy(), contract));
    }

    /** Resolves a responsible person, who must belong to the same contract as the pendency. */
    private ResponsiblePerson person(Long id, Contract contract) {
        if (id == null) {
            return null;
        }
        ResponsiblePerson person = responsiblePersonService.getEntity(id);
        if (!person.getContract().getId().equals(contract.getId())) {
            throw new BusinessRuleException(
                    "Responsible person %d does not belong to contract %d".formatted(id, contract.getId()));
        }
        return person;
    }
}
