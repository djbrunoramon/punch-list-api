package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByPriority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {

    @EntityGraph(attributePaths = {"contract", "registeredBy", "registeredTo", "finishedBy", "disapprovedBy",
            "canceledBy"})
    Page<Pendency> findAllByContractId(Long contractId, Pageable pageable);

    @EntityGraph(attributePaths = {"contract", "registeredBy", "registeredTo", "finishedBy", "disapprovedBy",
            "canceledBy"})
    Optional<Pendency> findWithRelationsById(Long id);

    long countByContractIdAndStatus(Long contractId, PendencyStatus status);

    @Query("""
            select new br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByPriority(p.priority, count(p))
            from Pendency p
            where p.contract.id = :contractId and p.status = :status
            group by p.priority
            order by p.priority
            """)
    List<PendencyByPriority> countByPriority(@Param("contractId") Long contractId,
                                             @Param("status") PendencyStatus status);
}
