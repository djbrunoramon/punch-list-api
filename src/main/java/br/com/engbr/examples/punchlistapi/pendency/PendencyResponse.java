package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.contract.ContractResponse;
import br.com.engbr.examples.punchlistapi.responsibleperson.ResponsiblePersonSummary;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PendencyResponse(
        Long id,
        String areaIdentification,
        String tag,
        String description,
        String priority,
        String discipline,
        PendencyStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime modifiedIn,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime expectedIn,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime finishedAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime disapprovedAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime canceledAt,
        ContractResponse contract,
        ResponsiblePersonSummary registeredBy,
        ResponsiblePersonSummary registeredTo,
        ResponsiblePersonSummary finishedBy,
        ResponsiblePersonSummary disapprovedBy,
        ResponsiblePersonSummary canceledBy) {
}
