package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class EmployeeNotExsitingException extends NotFoundException {
    public EmployeeNotExsitingException() {
        super(ExceptionMsg.EMPLOYEE_NOT_EXSITING);
    }
}
