package com.vhre.employee_leave_management.modules.leavetypes.mapper;

import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Mapper component responsible for converting between {@link LeaveType} domain entities
 * and their respective Data Transfer Objects (DTOs).
 * <p>
 * This interface is processed by MapStruct at compile time to generate the concrete
 * implementation managed as a Spring Bean.
 * </p>
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        // 💡 DESACTIVA EL USO DEL BUILDER EN MAPSTRUCT:
        // Esto obliga a MapStruct a usar los getters/setters tradicionales que sí ven la herencia
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface LeaveTypeMapper {

    /**
     * Converts a request DTO into a new LeaveType entity instance.
     * The technical identifier (ID) is explicitly ignored to allow the database
     * to handle primary key auto-generation safely during creation.
     *
     * @param dto the source request data transfer object
     * @return a partially populated LeaveType entity
     */
    @Mapping(target = "id", ignore = true)
    LeaveType toEntity(LeaveTypeRequestDTO dto);

    /**
     * Converts a LeaveType domain entity into a read-only response DTO.
     * This mapping automatically includes fields inherited from audited superclasses
     * such as timestamps and user identifiers.
     *
     * @param leaveType the source domain entity from the database
     * @return a fully populated LeaveTypeResponseDTO for client-side consumption
     */
    LeaveTypeResponseDTO toResponseDTO(LeaveType leaveType);

    /**
     * Performs an in-place update of an existing LeaveType entity instance using
     * the incoming data from a request DTO.
     * The database primary key (ID) is ignored to enforce immutability of the entity record track.
     *
     * @param dto       the source data containing the updated fields
     * @param leaveType the target database entity instance to be modified
     */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(LeaveTypeRequestDTO dto, @MappingTarget LeaveType leaveType);
}
