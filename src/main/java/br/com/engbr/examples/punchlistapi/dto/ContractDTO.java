package br.com.engbr.examples.punchlistapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ContractDTO {

    @NotBlank
    private String numberContract;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    private LocalDateTime startAt;

    private LocalDateTime scheduledTo;

    private BigDecimal estimatedAt;
}
