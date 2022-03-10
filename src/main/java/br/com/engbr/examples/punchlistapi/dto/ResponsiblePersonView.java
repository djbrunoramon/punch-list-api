package br.com.engbr.examples.punchlistapi.dto;

import java.time.LocalDateTime;

public interface ResponsiblePersonView {
    Long getId();

    String getName();

    String getOccupation();

    String getDepartment();

    LocalDateTime getCreatedAt();

    boolean isActive();

    ContractView getContract();
}
