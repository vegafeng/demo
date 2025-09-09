package org.example.demo.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super(ExceptionMsg.Page_Not_Found);
    }
}
