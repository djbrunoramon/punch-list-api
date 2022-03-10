package br.com.engbr.examples.punchlistapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends AbstractEntity {
    @Column(name = "number_contract", nullable = false)
    private String numberContract;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "scheduled_to")
    private LocalDateTime scheduledTo;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "inactive_at")
    private LocalDateTime inactiveAt;

    @Column(name = "estimated_at", columnDefinition = "decimal(15,2)")
    private BigDecimal estimatedAt;
}