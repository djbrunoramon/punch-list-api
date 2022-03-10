package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.ContractView;
import br.com.engbr.examples.punchlistapi.model.Contract;
import br.com.engbr.examples.punchlistapi.repositories.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public List<ContractView> findAll() {
        return contractRepository.findAllByActive(true);
    }
}
