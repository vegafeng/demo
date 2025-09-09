package org.example.demo.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super(ExceptionMsg.PAGE_NOT_FOUND);
    }
}
