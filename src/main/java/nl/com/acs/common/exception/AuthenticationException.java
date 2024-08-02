package nl.com.acs.common.exception;
import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_A_ERROR_002;

public class AuthenticationException extends FunctionalException{
    public AuthenticationException(String message,Throwable cause) {
        super(REG_A_ERROR_002.getCode(), message,cause);
    }
}
