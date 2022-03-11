package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.views.ContractView;
import br.com.engbr.examples.punchlistapi.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<ContractView> findAllByActive(boolean active);

}