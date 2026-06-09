package com.vhre.employee_leave_management.modules.leavetypes.controller;

import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;
import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import com.vhre.employee_leave_management.modules.leavetypes.service.LeaveTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leave-types")
@RequiredArgsConstructor
@Tag(
        name = "Leave Types",
        description = "Endpoints for managing corporate leave configurations and types (e.g., Maternity, Sick Leave, Vacation)."
)
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @PostMapping(value = "/form", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeaveTypeResponseDTO> createViaJson(
            @Valid @RequestBody LeaveTypeRequestDTO requestDTO
    ) {
        return new ResponseEntity<>(leaveTypeService.create(requestDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LeaveTypeResponseDTO> createViaForm(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                            schema = @Schema(implementation = LeaveTypeRequestDTO.class)
                    )
            )
            @ModelAttribute @Valid LeaveTypeRequestDTO requestDTO
    ) {
        return new ResponseEntity<>(leaveTypeService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get leave type by ID", description = "Fetches the full details of a leave configuration by its UUID.")
    @ApiResponse(responseCode = "200", description = "Leave type configuration found.")
    @ApiResponse(responseCode = "404", description = "Leave type not found.")
    public ResponseEntity<LeaveTypeResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveTypeService.getById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Find a leave type by its unique name", description = "Performs a semantic strict search to retrieve a specific leave type profile by its name.")
    @ApiResponse(responseCode = "200", description = "Leave type matched and returned.")
    @ApiResponse(responseCode = "404", description = "No leave type matches the provided name.")
    public ResponseEntity<LeaveTypeResponseDTO> getByName(@RequestParam String name) {
        return ResponseEntity.ok(leaveTypeService.getByName(name));
    }

    @GetMapping("/applicable")
    @Operation(
            summary = "Filter leave types by applicable genders",
            description = "Retrieves leave configurations that match a set of genders. Ideal for listing policies that apply to an employee's profile plus UNIVERSAL rules."
    )
    @ApiResponse(responseCode = "200", description = "Filtered list retrieved successfully.")
    public ResponseEntity<List<LeaveTypeResponseDTO>> getByGenders(@RequestParam Collection<Gender> genders) {
        return ResponseEntity.ok(leaveTypeService.getByApplicableGenders(genders));
    }

    @GetMapping
    @Operation(summary = "List all leave configurations", description = "Brings a full catalog of all active leave configurations inside the system matrix.")
    @ApiResponse(responseCode = "200", description = "Catalog list retrieved successfully.")
    public ResponseEntity<List<LeaveTypeResponseDTO>> getAll() {
        return ResponseEntity.ok(leaveTypeService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing leave type configuration", description = "Updates fields like standard allocation limits or applicable restrictions.")
    @ApiResponse(responseCode = "200", description = "Configuration updated successfully.")
    @ApiResponse(responseCode = "404", description = "Leave type target record not found.")
    @ApiResponse(responseCode = "409", description = "Name modification collides with another existing type configuration.")
    public ResponseEntity<LeaveTypeResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody LeaveTypeRequestDTO requestDTO) {
        return ResponseEntity.ok(leaveTypeService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a leave type policy configuration", description = "Safely archives the leave policy track out of active runtime use views.")
    @ApiResponse(responseCode = "204", description = "Configuration successfully detached and archived (No Content).")
    @ApiResponse(responseCode = "404", description = "Leave type target not found.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        leaveTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
