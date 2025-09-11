package org.example.demo.exception;

public class InvalidSalaryException extends BadRequestException {
    public InvalidSalaryException() {
        super(ExceptionMsg.INVALID_SALARY_EXCEPTION);
    }
}
