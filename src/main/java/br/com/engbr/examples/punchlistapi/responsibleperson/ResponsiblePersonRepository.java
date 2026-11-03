package br.com.engbr.examples.punchlistapi.responsibleperson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {

    @EntityGraph(attributePaths = "contract")
    Page<ResponsiblePerson> findAllByContractId(Long contractId, Pageable pageable);

    @EntityGraph(attributePaths = "contract")
    Optional<ResponsiblePerson> findWithContractById(Long id);
}
