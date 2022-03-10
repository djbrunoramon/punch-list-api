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
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pendency", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pendency extends AbstractEntity {

    @Column(name = "id_contract", nullable = false)
    private Long idContract;

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
    private StatusEnum status;

    @Column(name = "registered_by", nullable = false)
    private Long registeredBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_in", nullable = false)
    private LocalDateTime modifiedIn;

    @Column(name = "registered_to", nullable = false)
    private Long registeredTo;

    @Column(name = "expected_in")
    private LocalDateTime expectedIn;

    @Column(name = "finished_by")
    private Long finishedBy;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "disapproved_by")
    private Long disapprovedBy;

    @Column(name = "disapproved_at")
    private LocalDateTime disapprovedAt;

    @Column(name = "canceled_by")
    private Long canceledBy;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;
}
