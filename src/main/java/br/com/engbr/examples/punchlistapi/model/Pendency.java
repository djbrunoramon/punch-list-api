package br.com.engbr.examples.punchlistapi.model;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pendency", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pendency extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "id_contract")
    private Contract contract;

    @Column(name = "area_identification", nullable = false)
    private String areaIdentification;

    @Column(name = "tag")
    private String tag;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority", nullable = false)
    private String priority;

    @Column(name = "discipline", nullable = false)
    private String discipline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.OPEN;

    @OneToOne
    @JoinColumn(name = "registered_by", nullable = false)
    private ResponsiblePerson registeredBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_in", nullable = false)
    private LocalDateTime modifiedIn;

    @OneToOne
    @JoinColumn(name = "registered_to", nullable = false)
    private ResponsiblePerson registeredTo;

    @Column(name = "expected_in")
    private LocalDateTime expectedIn;

    @OneToOne
    @JoinColumn(name = "finished_by")
    private ResponsiblePerson finishedBy;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @OneToOne
    @JoinColumn(name = "disapproved_by")
    private ResponsiblePerson disapprovedBy;

    @Column(name = "disapproved_at")
    private LocalDateTime disapprovedAt;

    @OneToOne
    @JoinColumn(name = "canceled_by")
    private ResponsiblePerson canceledBy;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;
}
