package org.example.demo.exception;

public class IdNotExsitingException extends NotFoundException {
    public IdNotExsitingException() {
        super(ExceptionMsg.ID_NOT_EXSITING_EXCEPTION);
    }
}
