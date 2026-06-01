package com.vhre.employee_leave_management.core.exceptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard structure for API error responses.")
public record ErrorResponseDTO(
        @Schema(
                description = "HTTP Status Code",
                example = "409"
        )
        int status,

        @Schema(
                description = "Brief error category description",
                example = "Conflict"
        )
        String error,

        @Schema(
                description = "Detailed explanation of what caused the failure",
                example = "An employee with number EMP-001 already exists."
        )
        String message,

        @Schema(
                description = "Timestamp when the error occurred",
                example = "2026-05-31T23:34:04Z"
        )
        Instant timestamp
) { }
