package com.vhre.employee_leave_management.core.utils.auditable_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditableResponseDTO {
    @Schema(description = "Fecha de creación del registro", example = "2024-04-06T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de la última actualización", example = "2024-04-06T12:30:00")
    private LocalDateTime updatedAt;
}
