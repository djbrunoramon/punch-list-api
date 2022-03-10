package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}