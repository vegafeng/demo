package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class CompanyNotExsitingException extends Exception {
    public CompanyNotExsitingException() {
        super(ExceptionMsg.COMPANY_NOT_EXSITING);
    }
}
