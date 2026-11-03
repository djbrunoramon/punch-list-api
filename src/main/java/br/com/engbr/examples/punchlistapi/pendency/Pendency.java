package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.shared.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pendency", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
public class Pendency extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_contract", nullable = false)
    private Contract contract;

    @Column(name = "area_identification", nullable = false, length = 120)
    private String areaIdentification;

    @Column(name = "tag", length = 50)
    private String tag;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "priority", nullable = false, length = 20)
    private String priority;

    @Column(name = "discipline", nullable = false, length = 60)
    private String discipline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PendencyStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registered_by", nullable = false)
    private ResponsiblePerson registeredBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registered_to", nullable = false)
    private ResponsiblePerson registeredTo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_in", nullable = false)
    private LocalDateTime modifiedIn;

    @Column(name = "expected_in")
    private LocalDateTime expectedIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_by")
    private ResponsiblePerson finishedBy;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disapproved_by")
    private ResponsiblePerson disapprovedBy;

    @Column(name = "disapproved_at")
    private LocalDateTime disapprovedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canceled_by")
    private ResponsiblePerson canceledBy;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;
}
