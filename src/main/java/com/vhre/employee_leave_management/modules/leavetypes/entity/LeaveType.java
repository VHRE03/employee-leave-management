package com.vhre.employee_leave_management.modules.leavetypes.entity;

import com.vhre.employee_leave_management.core.utils.auditable_entity.AuditableEntity;
import com.vhre.employee_leave_management.modules.leavetypes.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "leave_types")
@SQLDelete(sql = "UPDATE leave_types SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class LeaveType extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_gender", nullable = false)
    private Gender applicableGender;

    @Column(name = "max_days_per_year", nullable = false)
    private Integer maxDaysPerYear;

    @Column(name = "max_consecutive_days", nullable = false)
    private Integer maxConsecutiveDays;

    @Builder.Default
    @Column(name = "min_advance_days", nullable = false)
    private Integer minAdvanceDays = 0;

    @Builder.Default
    @Column(name = "is_accumulable", nullable = false)
    private Boolean isAccumulable = false;

    @Column(name = "max_accumulation_days")
    private Integer maxAccumulationDays;

    @Builder.Default
    @Column(name = "requires_doc", nullable = false)
    private Boolean requiresDoc = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveType that = (LeaveType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PrePersist
    @PreUpdate
    private void validateBusinessRules() {
        if (Boolean.TRUE.equals(isAccumulable) && maxAccumulationDays == null) {
            throw new IllegalStateException(
                    "LeaveType '" + name + "': maxAccumulationDays must not be null when isAccumulable is true"
            );
        }

        if (maxDaysPerYear <= maxConsecutiveDays) {
            throw new IllegalStateException(
                    "LeaveType '" + name + "': maxDaysPerYear (" + maxDaysPerYear + ") must be greater than maxConsecutiveDays (" + maxConsecutiveDays + ")"
            );
        }

        if (maxAccumulationDays != null && maxDaysPerYear <= maxAccumulationDays) {
            throw new IllegalStateException(
                    "LeaveType '" + name + "': maxDaysPerYear (" + maxDaysPerYear +
                            ") must be greater than maxAccumulationDays (" + maxAccumulationDays + ")"
            );
        }
    }
}
