package org.example.demo.exception;

public class InvalidSalaryException extends RuntimeException {
    public InvalidSalaryException() {
        super(ExceptionMsg.INVALID_SALARY_EXCEPTION);
    }
}
