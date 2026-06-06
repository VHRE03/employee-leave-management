package com.vhre.employee_leave_management.modules.leavetypes.service;

import com.vhre.employee_leave_management.core.exceptions.ResourceNotFoundException;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;
import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import com.vhre.employee_leave_management.modules.leavetypes.mapper.LeaveTypeMapper;
import com.vhre.employee_leave_management.modules.leavetypes.repository.LeaveTypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;

    @Override
    @Transactional
    public LeaveTypeResponseDTO create(LeaveTypeRequestDTO requestDTO) {
        LeaveType leaveType = leaveTypeMapper.toEntity(requestDTO);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);

        return leaveTypeMapper.toResponseDTO(savedLeaveType);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveTypeResponseDTO getById(UUID id) {
        LeaveType levLeaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Type not found with ID: " + id));
        return leaveTypeMapper.toResponseDTO(levLeaveType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveTypeResponseDTO> getAll() {
        return leaveTypeRepository.findAll().stream()
                .map(leaveTypeMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public LeaveTypeResponseDTO update(UUID id, LeaveTypeRequestDTO requestDTO) {
        LeaveType existingLeaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Type not found with ID: " + id));

        leaveTypeMapper.updateEntityFromDTO(requestDTO, existingLeaveType);
        LeaveType updatedLeaveType = leaveTypeRepository.save(existingLeaveType);
        return leaveTypeMapper.toResponseDTO(updatedLeaveType);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!leaveTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Leave Type not found with ID: " + id);
        }
        leaveTypeRepository.deleteById(id);
    }
}
