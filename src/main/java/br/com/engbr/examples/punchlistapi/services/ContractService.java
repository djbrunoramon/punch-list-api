package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.ContractDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.model.Contract;
import br.com.engbr.examples.punchlistapi.repositories.ContractRepository;
import br.com.engbr.examples.punchlistapi.views.ContractView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public List<ContractView> findAll() {
        return contractRepository.listAll();
    }

    @Transactional(readOnly = true)
    public ContractView findById(Long id) throws IdNotFoundException {
        Optional<ContractView> contractView = contractRepository.readById(id);
        return contractView.orElseThrow(IdNotFoundException::new);
    }

    public ContractView save(ContractDTO contractDTO) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDTO, contract);
        contractRepository.save(contract);
        return contractRepository.readById(contract.getId()).get();
    }

    public ContractView update(Long id, ContractDTO contractDTO) throws IdNotFoundException {
        Contract contract = contractRepository.findById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(contractDTO, contract);
        contractRepository.save(contract);
        return contractRepository.readById(contract.getId()).get();
    }
}
