package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {
    List<ResponsiblePersonView> findAllByActive(boolean active);
}