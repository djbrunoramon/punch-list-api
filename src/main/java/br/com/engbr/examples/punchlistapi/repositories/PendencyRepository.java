package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.model.Pendency;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {

    Page<PendencyView> findAllByContractId(Long id, Pageable pageable);

    Optional<PendencyView> readById(Long id);
}
