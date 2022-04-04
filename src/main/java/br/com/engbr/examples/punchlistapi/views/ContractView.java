package br.com.engbr.examples.punchlistapi.views;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ContractView {
    Long getId();

    String getNumberContract();

    String getDescription();

    String getAddress();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getStartAt();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getScheduledTo();

    boolean isActive();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getInactiveAt();

    BigDecimal getEstimatedAt();
}
