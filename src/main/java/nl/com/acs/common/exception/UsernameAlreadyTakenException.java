package nl.com.acs.common.exception;
import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_F_ERROR_001;
public class UsernameAlreadyTakenException extends FunctionalException {
    public UsernameAlreadyTakenException(String message) {
        super(REG_F_ERROR_001.getCode(), message,null);
    }
}
