package com.vhre.employee_leave_management.modules.leaverequest.service;

import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestRequestDTO;
import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestResponseDTO;
import com.vhre.employee_leave_management.modules.leaverequest.entity.LeaveRequest;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service interface defining the business logic operations for managing Leave Requests
 * and handling their lifecycle workflows.
 */
public interface LeaveRequestService {
    /**
     * Submits a new leave request. Evaluates initial business constraints such as
     * date overlap checks and employee balances.
     *
     * @param requestDTO the details of the request to create
     * @return the saved leave request data tracking response
     */
    LeaveRequestResponseDTO create(LeaveRequestRequestDTO requestDTO);

    /**
     * Retrieves a single leave request by its unique technical identifier.
     *
     * @param id the UUID of the leave request
     * @return the detailed leave request payload
     */
    LeaveRequestResponseDTO getById(UUID id);

    /**
     * Retrieves the entire historical track of leave requests submitted by a single employee.
     *
     * @param employeeId the unique identifier of the target applicant employee
     * @return a list containing all leave requests matched to the employee track
     */
    List<LeaveRequestResponseDTO> getByEmployeeId(UUID employeeId);

    /**
     * Retrieves all active and historic leave requests registered in the system.
     * Can optionally filter records based on their operational state.
     *
     * @param status optional status parameter to filter results (e.g., PENDING, APPROVED).
     *               Pass null to retrieve all records.
     * @return a filtered or full list of leave request response DTOs
     */
    List<LeaveRequestResponseDTO> getAll(LeaveRequestStatus status);

    /**
     * Modifies the payload data of an existing leave request.
     * Business rules generally restrict modifications to records still in a PENDING state.
     *
     * @param id         the unique identifier of the request to modify
     * @param requestDTO the fresh updated dataset
     * @return the updated leave request record
     */
    LeaveRequestResponseDTO update(UUID id, LeaveRequestRequestDTO requestDTO);

    /**
     * Transition workflow processor to Approve or Reject a submitted leave request.
     * Implements validation checks on transition legality and updates organizational metrics.
     *
     * @param id           the target leave request identifier to update state
     * @param status       the final target state (e.g., APPROVED, REJECTED)
     * @param approvedById the unique identifier of the manager executing the operation
     * @return the newly modified leave request instance track
     */
    LeaveRequestResponseDTO updateStatus(UUID id, LeaveRequestStatus status, UUID approvedById);

    /**
     * Cancels or flags a leave request out of system views using a soft-delete mechanism.
     *
     * @param id the unique technical record identifier to remove
     */
    void delete(UUID id);
}
