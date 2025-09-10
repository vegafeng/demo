package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class AgeOutOfLegalRangeException extends RuntimeException {
    public AgeOutOfLegalRangeException() {
        super(ExceptionMsg.AGE_OUT_OF_LEGAL_RANGE);
    }
}
