package br.com.engbr.examples.punchlistapi.views;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;

import java.time.LocalDateTime;

public interface PendencyView {
    Long getId();

    String getAreaIdentification();

    String getTag();

    String getDescription();

    String getPriority();

    String getDiscipline();

    StatusEnum getStatus();

    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedIn();

    LocalDateTime getExpectedIn();

    LocalDateTime getFinishedAt();

    LocalDateTime getDisapprovedAt();

    LocalDateTime getCanceledAt();

    ContractView getContract();

    ResponsiblePersonView getRegisteredBy();

    ResponsiblePersonView getRegisteredTo();

    ResponsiblePersonView getFinishedBy();

    ResponsiblePersonView getDisapprovedBy();

    ResponsiblePersonView getCanceledBy();

}
