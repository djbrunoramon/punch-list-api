package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {

    Page<ResponsiblePersonView> findAllByContractId(Long id, Pageable pageable);

    @Query("select r from ResponsiblePerson r where r.id = ?1")
    Optional<ResponsiblePersonView> readById(Long id);
}
