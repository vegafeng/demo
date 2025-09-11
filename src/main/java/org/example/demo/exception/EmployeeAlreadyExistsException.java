package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class EmployeeAlreadyExistsException extends BadRequestException {
    public EmployeeAlreadyExistsException() {
        super(ExceptionMsg.EMPLOYEE_ALREADY_EXISTS_EXCEPTION);
    }
}
