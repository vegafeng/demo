package org.example.demo.exception.helper;

import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.exception.EmployeeNotExsitingException;
import org.example.demo.exception.ExceptionMsg;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author FENGVE
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<String> pageNotFound() {
        return new ResponseEntity<>(ExceptionMsg.PAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmployeeNotExsitingException.class)
    public ResponseEntity<String> employeeNotExisting() {
        return new ResponseEntity<>(ExceptionMsg.EMPLOYEE_NOT_EXSITING, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CompanyNotExsitingException.class)
    public ResponseEntity<String> companyNotExisting() {
        return new ResponseEntity<>(ExceptionMsg.COMPANY_NOT_EXSITING, HttpStatus.NOT_FOUND);
    }
}
