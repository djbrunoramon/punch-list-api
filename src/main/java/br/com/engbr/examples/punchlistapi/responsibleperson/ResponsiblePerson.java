package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.shared.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "responsible_person", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
public class ResponsiblePerson extends BaseEntity {

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "occupation", nullable = false, length = 120)
    private String occupation;

    @Column(name = "department", nullable = false, length = 120)
    private String department;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "inactive_in")
    private LocalDateTime inactiveIn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_contract", nullable = false)
    private Contract contract;
}
