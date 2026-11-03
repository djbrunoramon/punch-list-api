package br.com.engbr.examples.punchlistapi.responsibleperson;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Responsible person without the nested contract, for embedding in other features' responses.
 */
public record ResponsiblePersonSummary(
        Long id,
        String name,
        String occupation,
        String department,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime createdAt,
        boolean active) {
}
