package br.com.engbr.examples.punchlistapi.views;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface ResponsiblePersonView {
    Long getId();

    String getName();

    String getOccupation();

    String getDepartment();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getCreatedAt();

    boolean isActive();

    ContractView getContract();
}
