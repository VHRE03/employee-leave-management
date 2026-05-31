package com.vhre.employee_leave_management.modules.employee.dto;

import com.vhre.employee_leave_management.core.utils.auditable_dto.AuditableResponseDTO;
import com.vhre.employee_leave_management.modules.employee.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object representing the complete profile of an employee returned by the API.")
public class EmployeeResponseDTO extends AuditableResponseDTO {
    @Schema(
            description = "The unique identifier of the employee.",
            example = "b3d9f2a1-7c4e-4b2a-9e1d-8f3c5a6b7c8d"
    )
    private UUID id;

    @Schema(
            description = "The unique business/serial number of the employee.",
            example = "EMP-2026-0089"
    )
    private String number;

    @Schema(
            description = "The first name of the employee.",
            example = "Carlos"
    )
    private String firstName;

    @Schema(
            description = "The last name of the employee.",
            example = "Martinez"
    )
    private String lastName;

    @Schema(
            description = "The corporate email address of the employee.",
            example = "carlos.martinez@company.com"
    )
    private String email;

    @Schema(
            description = "The contact phone number of the employee.",
            example = "+525512345678"
    )
    private String phoneNumber;

    @Schema(
            description = "The assigned system role for the employee.",
            example = "STANDARD"
    )
    private EmployeeRole employeeRole;

    @Schema(
            description = "The official date the employee was hired.",
            example = "2024-01-15"
    )
    private LocalDate hireDate;

    @Schema(
            description = "Indicates whether the employee account is currently active.",
            example = "true"
    )
    private Boolean isActive;
}
