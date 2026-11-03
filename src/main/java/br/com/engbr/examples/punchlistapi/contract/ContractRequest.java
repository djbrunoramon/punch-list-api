package br.com.engbr.examples.punchlistapi.contract;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payload to create or update a contract. Server-managed fields (id, active, createdAt) are not accepted.
 */
public record ContractRequest(
        @NotBlank @Size(max = 50) String numberContract,
        @NotBlank @Size(max = 500) String description,
        @NotBlank @Size(max = 255) String address,
        LocalDateTime startAt,
        LocalDateTime scheduledTo,
        @DecimalMin("0.00") @Digits(integer = 13, fraction = 2) BigDecimal estimatedAt) {
}
