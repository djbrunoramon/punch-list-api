package br.com.engbr.examples.punchlistapi.repositories;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import br.com.engbr.examples.punchlistapi.model.Pendency;
import br.com.engbr.examples.punchlistapi.views.PendencyByPriorityView;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {

    @Query("select p from Pendency p where p.contract.id = ?1")
    Page<PendencyView> findAllByContractId(Long id, Pageable pageable);

    Optional<PendencyView> readById(Long id);

    @Query("select count(p) from Pendency p where p.contract.id = ?1 and p.status = ?2")
    long countByContractIdAndStatus(Long id, StatusEnum statusEnum);

    @Query("select p.priority AS priority, count(p) AS quantity from Pendency p " +
            "where p.contract.id = ?1 and p.status = ?2 " +
            "group by p.priority " +
            "order by p.priority ")
    List<PendencyByPriorityView> countPendencyByPriorityAndContractId(Long idContract, StatusEnum statusEnum);
}
