package com.vhre.employee_leave_management.modules.leavetypes.dto;

import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object (DTO) containing the necessary information to create or update an Leave Type.")
public record LeaveTypeRequestDTO(
        @Schema(
                description = "Unique and descriptive name of the leave type.",
                example = "Sick Leave",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Name is required.")
        @Size(max = 50, message = "Name must not exceed 50 characters.")
        String name,

        @Schema(
                description = "Gender to which this leave applies. If null, it applies universally to all employees.",
                example = "UNIVERSAL",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Gender applicableGender,

        @Schema(
                description = "Maximum number of total days allowed per calendar year.",
                example = "15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Maximum days per year is required.")
        @Min(value = 1, message = "Maximum days per year must be at least 1.")
        Integer maxDaysPerYear,

        @Schema(
                description = "Maximum consecutive days allowed per single request.",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Maximum consecutive days is required.")
        @Min(value = 1, message = "Maximum consecutive days must be at least 1.")
        Integer maxConsecutiveDays,

        @Schema(
                description = "Minimum number of days in advance the request must be submitted.",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Minimum advance days is required.")
        @Min(value = 0, message = "Minimum advance days cannot be negative.")
        Integer minAdvanceDays,

        @Schema(
                description = "Flag indicating if unused days can be carried over to the next year.",
                example = "false",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "isAccumulable flag is required.")
        Boolean isAccumulable,

        @Schema(
                description = "Maximum number of days that can be accumulated. Should be specified only if isAccumulable is true.",
                example = "10",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Min(value = 1, message = "Maximum accumulation days must be at least 1 if specified.")
        Integer maxAccumulationDays,

        @Schema(
                description = "Flag indicating if the employee must upload a supporting document (e.g., medical justification).",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "requiresDoc flag is required.")
        Boolean requiresDoc
) {
    /**
     * Compact constructor to enforce cross-field domain invariants during deserialization.
     */
    public LeaveTypeRequestDTO {
        // Guard Clause: Consecutive days cannot mathematically bypass yearly caps
        if (maxConsecutiveDays != null && maxDaysPerYear != null && maxConsecutiveDays > maxDaysPerYear) {
            throw new IllegalArgumentException("Maximum consecutive days cannot exceed the total maximum days per year.");
        }

        // Guard Clause: Enforces structural dependency when policy allows accumulation
        if (Boolean.TRUE.equals(isAccumulable) && maxAccumulationDays == null) {
            throw new IllegalArgumentException("Maximum accumulation days must be provided when the leave type is marked as accumulable.");
        }
    }
}
