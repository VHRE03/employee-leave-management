package com.vhre.employee_leave_management.modules.employee.repository;

import com.vhre.employee_leave_management.modules.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    // Unique business identifier queries
    boolean existsByNumber(String number);
    Optional<Employee> findByNumber(String number);

    // Uniqueness and validation queries for contacts
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // Auxiliary query to fetch active staff members
    List<Employee> findByIsActiveTrue();
}
