package com.vhre.employee_leave_management.modules.leaverequest.mapper;

import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestRequestDTO;
import com.vhre.employee_leave_management.modules.leaverequest.dto.LeaveRequestResponseDTO;
import com.vhre.employee_leave_management.modules.leaverequest.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeaveRequestMapper {
    @Mapping(target = "id", ignore = true)
    LeaveRequest toEntity(LeaveRequestRequestDTO dto);

    LeaveRequestResponseDTO toResponseDTO(LeaveRequest leaveRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(LeaveRequestRequestDTO dto, @MappingTarget LeaveRequest leaveRequest);
}
