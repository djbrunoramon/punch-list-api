package br.com.engbr.examples.punchlistapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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
