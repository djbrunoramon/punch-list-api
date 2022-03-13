package br.com.engbr.examples.punchlistapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "responsible_person", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsiblePerson extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "id_contract")
    private Contract contract;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "department", nullable = false)
    private String department;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "inactive_in")
    private LocalDateTime inactiveIn;

    public ResponsiblePerson(Long id) {
        this.setId(id);
    }
}
