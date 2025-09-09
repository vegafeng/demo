package org.example.demo.exception;

public class CompanyNotExsitingException extends Exception {
    public CompanyNotExsitingException() {
        super(ExceptionMsg.COMPANY_NOT_EXSITING);
    }
}
