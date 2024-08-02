package nl.com.acs.common.exception;
import static nl.com.acs.common.exception.TechnicalErrorCodes.REG_T_ERROR_002;

public class RegisterServiceException extends FunctionalException{
    public RegisterServiceException(String message) {
        super(REG_T_ERROR_002.getCode(), message,null);
    }
}
