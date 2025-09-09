package org.example.demo.exception;

public class EmployeeNotExsitingException extends RuntimeException {
    public EmployeeNotExsitingException() {
        super(ExceptionMsg.EMPLOYEE_NOT_EXSITING);
    }
}
