package com.vhre.employee_leave_management.modules.employee.service;

import com.vhre.employee_leave_management.modules.employee.dto.EmployeeRequestDTO;
import com.vhre.employee_leave_management.modules.employee.dto.EmployeeResponseDTO;
import com.vhre.employee_leave_management.modules.employee.entity.Employee;
import com.vhre.employee_leave_management.modules.employee.mapper.EmployeeMapper;
import com.vhre.employee_leave_management.modules.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeResponseDTO create(EmployeeRequestDTO requestDTO) {
        if (employeeRepository.existsByNumber(requestDTO.number())) {
            throw new RuntimeException("An employee with number " + requestDTO.number() + " already exists.");
        }
        if (employeeRepository.existsByEmail(requestDTO.email())) {
            throw new RuntimeException("An employee with email " + requestDTO.email() + " already exists.");
        }
        if (employeeRepository.existsByPhoneNumber(requestDTO.phoneNumber())) {
            throw new RuntimeException("An employee with phone number " + requestDTO.phoneNumber() + " already exists.");
        }

        Employee employee = employeeMapper.toEntity(requestDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponseDTO(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getByNumber(String number) {
        Employee employee = employeeRepository.findByNumber(number)
                .orElseThrow(() -> new RuntimeException("Employee not found with number: " + number));
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponseDTO update(UUID id, EmployeeRequestDTO requestDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        if (!existingEmployee.getNumber().equals(requestDTO.number())
                && employeeRepository.existsByNumber(requestDTO.number())) {
            throw new RuntimeException("Employee number is already taken: " + requestDTO.number());
        }
        if (!existingEmployee.getEmail().equals(requestDTO.email())
                && employeeRepository.existsByEmail(requestDTO.email())) {
            throw new RuntimeException("Email is already taken: " + requestDTO.email());
        }
        if (!existingEmployee.getPhoneNumber().equals(requestDTO.phoneNumber())
                && employeeRepository.existsByPhoneNumber(requestDTO.phoneNumber())) {
            throw new RuntimeException("Phone number is already taken: " + requestDTO.phoneNumber());
        }

        employeeMapper.updateEntityFromDTO(requestDTO, existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
