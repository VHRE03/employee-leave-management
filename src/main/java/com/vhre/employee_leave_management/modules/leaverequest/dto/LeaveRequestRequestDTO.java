package com.vhre.employee_leave_management.modules.leaverequest.dto;

import com.vhre.employee_leave_management.modules.leaverequest.enums.CompensationType;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "Data Transfer Object containing the necessary information to submit or modify a leave request.")
public record LeaveRequestRequestDTO(
        @Schema(
                description = "The ID of the employee making the request.",
                example = "7b293d18-2c93-4a12-8d41-3b7c26491a3c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Employee ID is required.")
        UUID employeeId,

        @Schema(
                description = "The ID of the requested leave type.",
                example = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Leave type ID is required.")
        UUID leaveTypeId,

        @Schema(
                description = "The start date of the leave period.",
                example = "2026-07-10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Start date is required.")
        LocalDate startDate,

        @Schema(
                description = "The end date of the leave period (inclusive).",
                example = "2026-07-15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "End date is required.")
        LocalDate endDate,

        @Schema(
                description = "Optional start time if the leave is for a partial day.",
                example = "08:00:00",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        LocalTime startTime,

        @Schema(
                description = "Optional end time if the leave is for a partial day.",
                example = "13:00:00",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        LocalTime endTime,

        @Schema(
                description = "Type of financial or time compensation applied to this leave.",
                example = "PAID",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Compensation type is required.")
        CompensationType compensationType,

        @Schema(
                description = "Optional status field. Typically used when an admin updates or approves the request.",
                example = "PENDING",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        LeaveRequestStatus status,

        @Schema(
                description = "The justification or reason for the leave request.",
                example = "Annual family medical checkup and rest.",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String reason
) {
    // Cross-field validation to ensure structural date integrity
    @AssertTrue(message = "The end date must be equal to or after the start date.")
    @Schema(hidden = true)
    public boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }
}
