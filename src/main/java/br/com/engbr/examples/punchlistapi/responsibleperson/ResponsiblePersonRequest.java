package br.com.engbr.examples.punchlistapi.responsibleperson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Payload to create or update a responsible person. Server-managed fields (id, active, createdAt) are not accepted.
 */
public record ResponsiblePersonRequest(
        @NotNull Long idContract,
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 120) String occupation,
        @NotBlank @Size(max = 120) String department) {
}
