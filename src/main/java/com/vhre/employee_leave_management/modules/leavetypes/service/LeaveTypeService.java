package com.vhre.employee_leave_management.modules.leavetypes.service;

import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeRequestDTO;
import com.vhre.employee_leave_management.modules.leavetypes.dto.LeaveTypeResponseDTO;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Service interface defining administration rules and lookups for corporate Leave Type configurations.
 */
public interface LeaveTypeService {

    LeaveTypeResponseDTO create(LeaveTypeRequestDTO requestDTO);

    LeaveTypeResponseDTO getById(UUID id);

    /**
     * Finds a specific leave configuration by its strictly unique name.
     *
     * @param name target policy name to lookup
     * @return the matched leave type configuration profile
     */
    LeaveTypeResponseDTO getByName(String name);

    /**
     * Looks up leave policies filtered by a target collection of applicable demographic genders.
     *
     * @param genders demographic match track keys (e.g., FEMALE + UNIVERSAL)
     * @return list of matching configured leave policies
     */
    List<LeaveTypeResponseDTO> getByApplicableGenders(Collection<Gender> genders);

    List<LeaveTypeResponseDTO> getAll();

    LeaveTypeResponseDTO update(UUID id, LeaveTypeRequestDTO requestDTO);

    void delete(UUID id);
}
