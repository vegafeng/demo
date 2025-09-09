package org.example.demo.exception.helper;

import org.example.demo.exception.ExceptionMsg;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<String> pageNotFound() {
        return new ResponseEntity<>(ExceptionMsg.Page_Not_Found, HttpStatus.NOT_FOUND);
    }
}
