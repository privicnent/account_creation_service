package nl.com.acs.common.exception;
import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_F_ERROR_003;

public class InvalidUsernameException extends FunctionalException{
    public InvalidUsernameException(String message) {
        super(REG_F_ERROR_003.getCode(), message,null);
    }
}
