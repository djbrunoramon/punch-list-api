package br.com.engbr.examples.punchlistapi.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContractResponse(
        Long id,
        String numberContract,
        String description,
        String address,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime scheduledTo,
        boolean active,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime inactiveAt,
        BigDecimal estimatedAt) {
}
