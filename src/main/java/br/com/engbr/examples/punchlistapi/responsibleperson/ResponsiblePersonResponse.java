package br.com.engbr.examples.punchlistapi.responsibleperson;

import br.com.engbr.examples.punchlistapi.contract.ContractResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ResponsiblePersonResponse(
        Long id,
        String name,
        String occupation,
        String department,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime createdAt,
        boolean active,
        ContractResponse contract) {
}
