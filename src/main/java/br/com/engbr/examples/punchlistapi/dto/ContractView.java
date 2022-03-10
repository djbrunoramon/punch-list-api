package br.com.engbr.examples.punchlistapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ContractView {
    Long getId();

    String getNumberContract();

    String getDescription();

    String getAddress();

    LocalDateTime getStartAt();

    LocalDateTime getScheduledTo();

    boolean isActive();

    LocalDateTime getInactiveAt();

    BigDecimal getEstimatedAt();
}
