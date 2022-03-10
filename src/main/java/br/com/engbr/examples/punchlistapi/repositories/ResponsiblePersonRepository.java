package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {
}