package nl.com.acs.common.exception;
public class FunctionalException extends RuntimeException {

    private final String errorCode;
    protected FunctionalException(final String errorCode, final String message,final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}
