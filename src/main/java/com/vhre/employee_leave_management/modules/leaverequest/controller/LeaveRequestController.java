package com.vhre.employee_leave_management.modules.leaverequest.controller;

import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestRequestDTO;
import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestResponseDTO;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import com.vhre.employee_leave_management.modules.leaverequest.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-request")
@RequiredArgsConstructor
@Tag(
        name = "Leave Request",
        description = "Endpoints for managing corporate leave request records."
)
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @PostMapping
    @Operation(summary = "Submit a new leave request", description = "Creates a new leave request in PENDING status. Validates date ranges and constraints.")
    @ApiResponse(responseCode = "201", description = "Leave request submitted successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid payload or date range constraints broken.")
    @ApiResponse(responseCode = "409", description = "Business rule violation (e.g., insufficient available days).")
    public ResponseEntity<LeaveRequestResponseDTO> create(@Valid @RequestBody LeaveRequestRequestDTO requestDTO) {
        return new ResponseEntity<>(leaveRequestService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get leave request by ID", description = "Fetches a specific leave request record using its unique UUID.")
    @ApiResponse(responseCode = "200", description = "Leave request found.")
    @ApiResponse(responseCode = "404", description = "Leave request not found.")
    public ResponseEntity<LeaveRequestResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveRequestService.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "List leave requests by Employee", description = "Retrieves the complete historical list of leave requests belonging to a specific employee.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(leaveRequestService.getByEmployeeId(employeeId));
    }

    @GetMapping
    @Operation(summary = "List all leave requests", description = "Retrieves all registered leave requests. Can be optionally filtered by status (ideal for HR/Admin dashboards).")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getAll(@RequestParam(required = false) LeaveRequestStatus status) {
        return ResponseEntity.ok(leaveRequestService.getAll(status));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a pending leave request", description = "Modifies an existing leave request. Business rules usually restrict this operation to requests that are still PENDING.")
    @ApiResponse(responseCode = "200", description = "Leave request updated successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid payload constraints.")
    @ApiResponse(responseCode = "404", description = "Leave request not found.")
    @ApiResponse(responseCode = "409", description = "Request cannot be modified because its status is no longer PENDING.")
    public ResponseEntity<LeaveRequestResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody LeaveRequestRequestDTO requestDTO) {
        return ResponseEntity.ok(leaveRequestService.update(id, requestDTO));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Process a leave request (Approve/Reject)", description = "Workflow endpoint used by managers to approve or reject a pending leave request.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully.")
    @ApiResponse(responseCode = "404", description = "Leave request or Approver Employee not found.")
    @ApiResponse(responseCode = "409", description = "Invalid status transition.")
    public ResponseEntity<LeaveRequestResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestParam LeaveRequestStatus status,
            @RequestParam UUID approvedById) {
        // Enforces workflow processing and records who made the decision
        return ResponseEntity.ok(leaveRequestService.updateStatus(id, status, approvedById));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel/Soft-delete a leave request", description = "Performs a soft delete on the leave request, marking it as removed from active tracks.")
    @ApiResponse(responseCode = "204", description = "Leave request successfully cancelled (No Content).")
    @ApiResponse(responseCode = "404", description = "Leave request not found.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        leaveRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
