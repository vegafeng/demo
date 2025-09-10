package org.example.demo.exception.helper;

import org.example.demo.exception.*;
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
    @ExceptionHandler(AgeOutOfLegalRangeException.class)
    public ResponseEntity<String> ageOutOfLegalRange() {
        return new ResponseEntity<>(ExceptionMsg.AGE_OUT_OF_LEGAL_RANGE, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IdNotExsitingException.class)
    public ResponseEntity<String> idNotExsiting() {
        return new ResponseEntity<>(ExceptionMsg.ID_NOT_EXSITING_EXCEPTION, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidSalaryException.class)
    public ResponseEntity<String> invalidSalary() {
        return new ResponseEntity<>(ExceptionMsg.INVALID_SALARY_EXCEPTION, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<String> employeeAlreadyExists() {
        return new ResponseEntity<>(ExceptionMsg.EMPLOYEE_ALREADY_EXISTS_EXCEPTION, HttpStatus.BAD_REQUEST);

    }
}
