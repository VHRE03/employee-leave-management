package com.vhre.employee_leave_management.modules.leaverequest.dto;

import com.vhre.employee_leave_management.modules.leaverequest.enums.CompensationType;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data Transfer Object representing a fully detailed record of a Leave Request transaction.")
public class LeaveRequestResponseDTO {

    @Schema(description = "Unique identifier (UUID) of the leave request record.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
    private UUID id;

    @Schema(description = "ID of the employee who created the request.", example = "7b293d18-2c93-4a12-8d41-3b7c26491a3c")
    private UUID employeeId;

    @Schema(description = "ID of the specific configuration of leave requested.", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID leaveTypeId;

    @Schema(description = "ID of the manager/approver who reviewed the request. Returns null if still pending.", example = "1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d")
    private UUID approvedById;

    @Schema(description = "The start date of the requested period.", example = "2026-07-10")
    private LocalDate startDate;

    @Schema(description = "The end date of the requested period.", example = "2026-07-15")
    private LocalDate endDate;

    @Schema(description = "Start time if the leave is partial.", example = "08:00:00")
    private LocalTime startTime;

    @Schema(description = "End time if the leave is partial.", example = "13:00:00")
    private LocalTime endTime;

    @Schema(description = "Total business or calendar days calculated by the system for this request.", example = "5")
    private Integer daysRequested;

    @Schema(description = "Current lifecycle state of the request.", example = "APPROVED")
    private LeaveRequestStatus status;

    @Schema(description = "The financial nature of the leave compensation mapping.", example = "UNPAID")
    private CompensationType compensationType;

    @Schema(description = "Salary deduction percentage applied, if applicable.", example = "100.00")
    private BigDecimal deductionPercentage;

    @Schema(description = "Total estimated money deducted from payroll. Calculated dynamically.", example = "350.50")
    private Float deductionAmount;

    @Schema(description = "The contextual description written by the applicant.", example = "Annual family medical checkup and rest.")
    private String reason;

    @Schema(description = "Timestamp when the request was originally created.", example = "2026-06-05T10:15:30Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the request was last updated or changed state.", example = "2026-06-05T22:11:00Z")
    private Instant updatedAt;
}
