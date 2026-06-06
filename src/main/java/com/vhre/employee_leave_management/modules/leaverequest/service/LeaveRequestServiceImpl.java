package com.vhre.employee_leave_management.modules.leaverequest.service;

import com.vhre.employee_leave_management.core.exceptions.ResourceConflictException;
import com.vhre.employee_leave_management.core.exceptions.ResourceNotFoundException;
import com.vhre.employee_leave_management.modules.employee.entity.Employee;
import com.vhre.employee_leave_management.modules.employee.repository.EmployeeRepository;
import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestRequestDTO;
import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestResponseDTO;
import com.vhre.employee_leave_management.modules.leaverequest.entity.LeaveRequest;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import com.vhre.employee_leave_management.modules.leaverequest.mapper.LeaveRequestMapper;
import com.vhre.employee_leave_management.modules.leaverequest.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestMapper leaveRequestMapper;

    @Override
    @Transactional
    public LeaveRequestResponseDTO create(LeaveRequestRequestDTO requestDTO) {
        // Enforces initial status as PENDING on creation
        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(requestDTO);
        leaveRequest.setStatus(LeaveRequestStatus.PENDING);

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toResponseDTO(savedLeaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveRequestResponseDTO getById(UUID id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found with ID: " + id));
        return leaveRequestMapper.toResponseDTO(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequestResponseDTO> getByEmployeeId(UUID employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId).stream()
                // Casts the Object explicitly to LeaveRequest to clear the compiler gap
                .map(request -> leaveRequestMapper.toResponseDTO((LeaveRequest) request))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequestResponseDTO> getAll(LeaveRequestStatus status) {
        // If no status is specified, brings everything; otherwise filters dynamically
        List<LeaveRequest> requests = (status == null)
                ? leaveRequestRepository.findAll()
                : leaveRequestRepository.findByStatus(status);

        return requests.stream()
                .map(leaveRequestMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public LeaveRequestResponseDTO update(UUID id, LeaveRequestRequestDTO requestDTO) {
        LeaveRequest existingLeaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found with ID: " + id));

        // Guard clause: Users can only modify a request if it has not been reviewed yet
        if (existingLeaveRequest.getStatus() != LeaveRequestStatus.PENDING) {
            throw new ResourceConflictException("Leave request cannot be modified because it is already " + existingLeaveRequest.getStatus());
        }

        leaveRequestMapper.updateEntityFromDTO(requestDTO, existingLeaveRequest);

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(existingLeaveRequest);
        return leaveRequestMapper.toResponseDTO(savedLeaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequestResponseDTO updateStatus(UUID id, LeaveRequestStatus status, UUID approvedById) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found with ID: " + id));

        // Guard clause: Prevents reprocessing an already finalized request track
        if (leaveRequest.getStatus() != LeaveRequestStatus.PENDING) {
            throw new ResourceConflictException("Leave request has already been processed and is currently " + leaveRequest.getStatus());
        }

        // Verifies the manager entity exists in the organization ecosystem
        Employee approver = employeeRepository.findById(approvedById)
                .orElseThrow(() -> new ResourceNotFoundException("Approver Employee not found with ID: " + approvedById));

        // Executes state transition and records auditing identity
        leaveRequest.setStatus(status);
        leaveRequest.setApprovedBy(approver);

        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toResponseDTO(updatedLeaveRequest);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!leaveRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Leave Request not found with ID: " + id);
        }
        leaveRequestRepository.deleteById(id);
    }
}
