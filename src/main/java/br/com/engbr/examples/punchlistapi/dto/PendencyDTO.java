package br.com.engbr.examples.punchlistapi.dto;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class PendencyDTO {

    @NotNull
    private Long idContract;

    @NotBlank
    private String areaIdentification;

    private String tag;

    @NotBlank
    private String description;

    @NotBlank
    private String priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @NotBlank
    private String discipline;

    @NotNull
    private Long registeredBy;

    @NotNull
    private Long registeredTo;

    private LocalDateTime expectedIn;

    private Long finishedBy;

    private LocalDateTime finishedAt;

    private Long disapprovedBy;

    private LocalDateTime disapprovedAt;

    private Long canceledBy;

    private LocalDateTime canceledAt;
}
