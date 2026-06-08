package com.vhre.employee_leave_management.modules.leavetypes.dto;

import com.vhre.employee_leave_management.core.utils.auditable_dto.AuditableResponseDTO;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object representing a fully populated Leave Type record sent back to the client.")
public class LeaveTypeResponseDTO extends AuditableResponseDTO {
    @Schema(
            description = "Unique database identifier (UUID) of the leave type.",
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
    )
    private UUID id;

    @Schema(
            description = "Unique and descriptive name of the leave type.",
            example = "Sick Leave"
    )
    private String name;

    @Schema(
            description = "Gender identity to which this leave applies.",
            example = "UNIVERSAL"
    )
    private Gender applicableGender;

    @Schema(
            description = "Maximum number of total days allowed per calendar year.",
            example = "15"
    )
    private Integer maxDaysPerYear;

    @Schema(
            description = "Maximum consecutive days allowed per single request.",
            example = "5"
    )
    private Integer maxConsecutiveDays;

    @Schema(
            description = "Minimum number of days in advance the request must be submitted.",
            example = "2"
    )
    private Integer minAdvanceDays;

    @Schema(
            description = "Flags if unused days can be carried over to the next calendar year.",
            example = "false"
    )
    private Boolean isAccumulable;

    @Schema(
            description = "Maximum number of days that can be accumulated. Returns null if isAccumulable is false.",
            example = "10"
    )
    private Integer maxAccumulationDays;

    @Schema(
            description = "Flags if the employee must upload a supporting document/justification.",
            example = "true"
    )
    private Boolean requiresDoc;
}
