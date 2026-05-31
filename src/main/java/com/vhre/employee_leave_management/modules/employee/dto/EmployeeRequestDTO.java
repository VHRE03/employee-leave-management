package com.vhre.employee_leave_management.modules.employee.dto;

import com.vhre.employee_leave_management.modules.employee.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Data Transfer Object (DTO) containing the necessary information to create or update an Employee.")
public record EmployeeRequestDTO(
        @Schema(
                description = "The unique business/serial number assigned to the employee.",
                example = "EMP-2026-0089",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Employee number is required.")
        @Size(max = 20, message = "Employee number must not exceed 20 characters.")
        String number,

        @Schema(
                description = "The first name of the employee.",
                example = "Carlos",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "First name is required and cannot be empty.")
        @Size(max = 50, message = "First name must not exceed 50 characters.")
        String firstName,

        @Schema(
                description = "The last name of the employee.",
                example = "Martinez",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Last name is required and cannot be empty.")
        @Size(max = 50, message = "Last name must not exceed 50 characters.")
        String lastName,

        @Schema(
                description = "The corporate email address of the employee.",
                example = "carlos.martinez@company.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email is required.")
        @Size(max = 100, message = "Email must not exceed 100 characters.")
        @Email(message = "Must be a well-formed email address.")
        String email,

        @Schema(
                description = "The contact phone number of the employee, supporting optional country codes.",
                example = "+525512345678",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Phone number is required.")
        @Size(max = 15, message = "Phone number must not exceed 15 characters.")
        @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Phone number must contain only digits (and an optional leading '+') and be between 10 and 15 characters.")
        String phoneNumber,

        @Schema(
                description = "The assigned role defining the system permissions for the employee.",
                example = "STANDARD",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Employee role is required.")
        EmployeeRole employeeRole,

        @Schema(
                description = "The date the employee was officially hired.",
                example = "2024-01-15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Hire date is required.")
        @PastOrPresent(message = "Hire date cannot be a date in the future.")
        LocalDate hireDate,

        @Schema(
                description = "Indicates whether the employee is currently active. If omitted, the system defaults to true.",
                example = "true",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Boolean isActive
) {
}
