package com.vhre.employee_leave_management.modules.leaverequest.repository;

import com.vhre.employee_leave_management.modules.leaverequest.entity.LeaveRequest;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {
    Collection<Object> findByEmployeeId(UUID employeeId);

    List<LeaveRequest> findByStatus(LeaveRequestStatus status);
}
