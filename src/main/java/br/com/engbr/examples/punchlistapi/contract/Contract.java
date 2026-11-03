package br.com.engbr.examples.punchlistapi.contract;

import br.com.engbr.examples.punchlistapi.shared.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract", schema = "punch_list")
@Getter
@Setter
@NoArgsConstructor
public class Contract extends BaseEntity {

    @Column(name = "number_contract", nullable = false, length = 50)
    private String numberContract;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "scheduled_to")
    private LocalDateTime scheduledTo;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "inactive_at")
    private LocalDateTime inactiveAt;

    @Column(name = "estimated_at", precision = 15, scale = 2)
    private BigDecimal estimatedAt;
}
