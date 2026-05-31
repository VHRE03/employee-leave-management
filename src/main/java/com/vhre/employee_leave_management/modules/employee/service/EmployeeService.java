package com.vhre.employee_leave_management.modules.employee.service;

import com.vhre.employee_leave_management.modules.employee.dto.EmployeeRequestDTO;
import com.vhre.employee_leave_management.modules.employee.dto.EmployeeResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface defining the business contract for managing Employee lifecycles.
 * Provides standard CRUD operations and handles core business validation rules.
 */
public interface EmployeeService {
    /**
     * Registers a new employee inthe system.
     * Validates that the employee number, email, and phone number are unique before persistence.
     *
     * @param requestDTO the payload containing the new employee's details
     * @return the saved employee formatted as a response DTO
     * @throws RuntimeException if number, email, or phone number is already registered
     */
    EmployeeResponseDTO create(EmployeeRequestDTO requestDTO);

    /**
     * Retrieves an employee profile by their unique identifier (UUID).
     *
     * @param id the unique UUID of the employee
     * @return the found employee details
     * @throws RuntimeException if no employee matches the given ID
     */
    EmployeeResponseDTO getById(UUID id);

    /**
     * Retrieves an employee profile by their unique business number.
     *
     * @param number the business identifier string (e.g., EMP-001)
     * @return the found employee details
     * @throws RuntimeException if no employee matches the given business number
     */
    EmployeeResponseDTO getByNumber(String number);

    /**
     * Retrieves all non-deleted employees currently registered in the system.
     *
     * @return a list of all employee response payloads
     */
    List<EmployeeResponseDTO> getAll();

    /**
     * Updates an existing employee's profile information.
     * Cross-checks unique constraints if the number, email, or phone number is being modified.
     *
     * @param id the unique UUID of the employee to update
     * @param requestDTO the payload containing the update information
     * @return the updated employee details
     * @throws RuntimeException if the employee is not found or updates violate uniqueness
     */
    EmployeeResponseDTO update(UUID id, EmployeeRequestDTO requestDTO);

    /**
     * Performs a logical (soft) delete on an employee profile.
     *
     * @param id the unique UUID of the employee to be soft-deleted
     * @throws RuntimeException if the employee does not exist
     */
    void delete(UUID id);
}
