package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class EmployeeNotExsitingException extends RuntimeException {
    public EmployeeNotExsitingException() {
        super(ExceptionMsg.EMPLOYEE_NOT_EXSITING);
    }
}
