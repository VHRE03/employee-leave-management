package com.vhre.employee_leave_management.modules.leavetypes.service;

import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LeaveTypeService {
    LeaveTypeResponseDTO create(LeaveTypeRequestDTO requestDTO);
    LeaveTypeResponseDTO getById(UUID id);
    List<LeaveTypeResponseDTO> getAll();
    LeaveTypeResponseDTO update(UUID id, LeaveTypeRequestDTO requestDTO);
    void delete(UUID id);
}
