package com.vhre.employee_leave_management.modules.employee.controller;

import com.vhre.employee_leave_management.modules.employee.dto.EmployeeRequestDTO;
import com.vhre.employee_leave_management.modules.employee.dto.EmployeeResponseDTO;
import com.vhre.employee_leave_management.modules.employee.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(
        name = "Employee Management",
        description = "Endpoints for managing corporate employee records and profiles."
)
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(
            summary = "Register a new employee",
            description = "Creates a new employee profile. Validates and enforces unique constraints on email, phone, and business number."
    )
    @ApiResponse(responseCode = "201", description = "Employee created successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid input payload.")
    @ApiResponse(responseCode = "409", description = "Unique constraint violation (duplication).")
    public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return new ResponseEntity<>(employeeService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get employee by Technical ID (UUID)",
            description = "Fetches a single employee record using its database primary key."
    )
    @ApiResponse(responseCode = "200", description = "Employee found.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping("/number/{number}")
    @Operation(
            summary = "Get employee by Business Number",
            description = "Fetches a single employee record using their human-readable company serial number."
    )
    @ApiResponse(responseCode = "200", description = "Employee found.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    public ResponseEntity<EmployeeResponseDTO> getByNumber(@PathVariable String number) {
        return ResponseEntity.ok(employeeService.getByNumber(number));
    }

    @GetMapping
    @Operation(
            summary = "List all employees",
            description = "Retrieves a complete list of all registered employees."
    )
    @ApiResponse(responseCode = "200", description = "List retrieved successfully.")
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an employee profile",
            description = "Modifies an existing employee's data by their UUID. Re-validates contact uniqueness if changes occur."
    )
    @ApiResponse(responseCode = "200", description = "Employee updated successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @ApiResponse(responseCode = "409", description = "Updated fields conflict with an existing record.")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.ok(employeeService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Soft-delete an employee",
            description = "Flags an employee as inactive/deleted without stripping the database row."
    )
    @ApiResponse(responseCode = "204", description = "Employee successfully deactivated (No Content).")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
