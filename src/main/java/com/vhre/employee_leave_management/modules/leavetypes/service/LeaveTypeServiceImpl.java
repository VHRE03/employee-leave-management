package com.vhre.employee_leave_management.modules.leavetypes.service;

import com.vhre.employee_leave_management.core.exceptions.ResourceConflictException;
import com.vhre.employee_leave_management.core.exceptions.ResourceNotFoundException;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;
import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import com.vhre.employee_leave_management.modules.leavetypes.mapper.LeaveTypeMapper;
import com.vhre.employee_leave_management.modules.leavetypes.repository.LeaveTypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        if (leaveTypeRepository.existsByName(requestDTO.name())) {
            throw new ResourceConflictException("A leave type configuration already exists with the name: " + requestDTO.name());
        }

        LeaveType leaveType = leaveTypeMapper.toEntity(requestDTO);
        LeaveType savedLeaveType = leaveTypeRepository.saveAndFlush(leaveType);
        return leaveTypeMapper.toResponseDTO(savedLeaveType);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveTypeResponseDTO getById(UUID id) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Type not found with ID: " + id));
        return leaveTypeMapper.toResponseDTO(leaveType);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveTypeResponseDTO getByName(String name) {
        LeaveType leaveType = leaveTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Type configuration not found with name: " + name));
        return leaveTypeMapper.toResponseDTO(leaveType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveTypeResponseDTO> getByApplicableGenders(Collection<Gender> genders) {
        return leaveTypeRepository.findByApplicableGenderIn(genders).stream()
                .map(leaveTypeMapper::toResponseDTO)
                .toList();
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

        if (!existingLeaveType.getName().equalsIgnoreCase(requestDTO.name())
                && leaveTypeRepository.existsByName(requestDTO.name())) {
            throw new ResourceConflictException("Cannot rename. Another leave configuration already uses the name: " + requestDTO.name());
        }

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
