package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.dto.ContractView;
import br.com.engbr.examples.punchlistapi.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<ContractView> findAllByActive(boolean active);

}