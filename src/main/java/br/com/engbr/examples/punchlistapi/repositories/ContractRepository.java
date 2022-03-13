package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.views.ContractView;
import br.com.engbr.examples.punchlistapi.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("select c from Contract c")
    List<ContractView> listAll();

    @Query("select c from Contract c where c.id = ?1")
    Optional<ContractView> readById(Long id);
}
