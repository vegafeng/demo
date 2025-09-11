package org.example.demo.exception;

/**
 * @author FENGVE
 */
public class AgeOutOfLegalRangeException extends BadRequestException {
    public AgeOutOfLegalRangeException() {
        super(ExceptionMsg.AGE_OUT_OF_LEGAL_RANGE);
    }
}
