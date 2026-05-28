package com.vhre.employee_leave_management.modules.leaverequest;

import com.vhre.employee_leave_management.core.utils.auditable_entity.AuditableEntity;
import com.vhre.employee_leave_management.modules.employee.entity.Employee;
import com.vhre.employee_leave_management.modules.leaverequest.enums.CompensationType;
import com.vhre.employee_leave_management.modules.leaverequest.enums.LeaveRequestStatus;
import com.vhre.employee_leave_management.modules.leavetypes.entity.LeaveType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "leave_requests")
@SQLDelete(sql = "UPDATE leave_requests SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class LeaveRequest extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private Employee approvedBy;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "days_requested")
    private Integer daysRequested;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveRequestStatus status = LeaveRequestStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "compensation_type", nullable = false)
    private CompensationType compensationType;

    @Column(name = "deduction_percentage", precision = 5, scale = 2)
    private BigDecimal deductionPercentage;

    @Column(name = "deduction_amount", precision = 10, scale = 2)
    private Float deductionAmount;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveRequest that = (LeaveRequest) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
