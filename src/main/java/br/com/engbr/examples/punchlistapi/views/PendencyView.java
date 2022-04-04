package br.com.engbr.examples.punchlistapi.views;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface PendencyView {
    Long getId();

    String getAreaIdentification();

    String getTag();

    String getDescription();

    String getPriority();

    String getDiscipline();

    StatusEnum getStatus();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getModifiedIn();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getExpectedIn();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getFinishedAt();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getDisapprovedAt();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime getCanceledAt();

    ContractView getContract();

    ResponsiblePersonView getRegisteredBy();

    ResponsiblePersonView getRegisteredTo();

    ResponsiblePersonView getFinishedBy();

    ResponsiblePersonView getDisapprovedBy();

    ResponsiblePersonView getCanceledBy();

}
