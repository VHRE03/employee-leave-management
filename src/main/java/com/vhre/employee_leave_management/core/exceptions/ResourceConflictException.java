package com.vhre.employee_leave_management.core.exceptions;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
