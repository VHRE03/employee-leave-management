package com.vhre.employee_leave_management.modules.leavetypes.repository;

import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, UUID> {

    // Prevent duplicities during creation and update operations
    boolean existsByName(String name);

    // Allows semantic searches by leave type name
    Optional<LeaveType> findByName(String name);

    /**
     * Retrieves leave types filtered by a collection of applicable genders.
     * Used to pass both the specific employee's gender and the UNIVERSAL track.
     */
    List<LeaveType> findByApplicableGenderIn(Collection<Gender> genders);
}
