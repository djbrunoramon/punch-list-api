package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.views.PendencyView;
import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import br.com.engbr.examples.punchlistapi.model.Pendency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {

    List<PendencyView> findAllByStatus(StatusEnum status);
}