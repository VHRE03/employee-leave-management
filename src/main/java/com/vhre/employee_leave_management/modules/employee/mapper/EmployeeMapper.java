package com.vhre.employee_leave_management.modules.employee.mapper;

import com.vhre.employee_leave_management.modules.employee.dto.EmployeeRequestDTO;
import com.vhre.employee_leave_management.modules.employee.dto.EmployeeResponseDTO;
import com.vhre.employee_leave_management.modules.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Data mapping interface powered by MapStruct to handle conversions between
 * {@link Employee} domain entities and their respective Data Transfer Objects (DTO).
 * <p>
 * Registered as a Spring Bean using {@link MappingConstants.ComponentModel#SPRING}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    /**
     * Maps an incoming {@link EmployeeRequestDTO} to a new {@link Employee} domain entity.
     * The database-managed ID is explicitly ignored during creation.
     *
     * @param dto the source request payload containing employee details.
     * @return a mapped Employee entity ready for persistence.
     */
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequestDTO dto);

    /**
     * Maps a persisted {@link Employee} domain entity into an outbound {@link EmployeeResponseDTO}.
     * Audit details from base classes are automatically handled if property names match.
     *
     * @param employee the source entity retrieved from the database
     * @return the formatted response payload for client consumption
     */
    EmployeeResponseDTO toResponseDTO(Employee employee);

    /**
     * Merges update data from an {@link EmployeeRequestDTO} directly into an existing {@link Employee} entity instance.
     * The primary key ID remains unchanged to ensure system integrity.
     *
     * @param dto the source data transfer object containing updated fields
     * @param employee the target managed entity to be modified in-place
     */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(EmployeeRequestDTO dto, @MappingTarget Employee employee);
}
