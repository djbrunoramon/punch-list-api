package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.model.Pendency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {
}