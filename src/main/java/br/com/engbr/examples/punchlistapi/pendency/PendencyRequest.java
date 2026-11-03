package br.com.engbr.examples.punchlistapi.pendency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Payload to create or update a pendency. Server-managed fields (id, createdAt, modifiedIn) are not accepted.
 * Status-dependent requirements are enforced by {@link PendencyStatusValidator}.
 */
public record PendencyRequest(
        @NotNull Long idContract,
        @NotBlank @Size(max = 120) String areaIdentification,
        @Size(max = 50) String tag,
        @NotBlank @Size(max = 2000) String description,
        @NotBlank @Size(max = 20) String priority,
        @NotNull PendencyStatus status,
        @NotBlank @Size(max = 60) String discipline,
        @NotNull Long registeredBy,
        @NotNull Long registeredTo,
        LocalDateTime expectedIn,
        Long finishedBy,
        LocalDateTime finishedAt,
        Long disapprovedBy,
        LocalDateTime disapprovedAt,
        Long canceledBy,
        LocalDateTime canceledAt) {
}
