package nl.com.acs.common.advice;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.exception.AuthenticationException;
import nl.com.acs.common.exception.InvalidUsernameException;
import nl.com.acs.common.exception.UsernameAlreadyTakenException;
import nl.com.acs.model.ResponseMessageWithError;
import nl.com.acs.model.ResponseWithValidationErrors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_F_ERROR_002;
import static nl.com.acs.common.exception.TechnicalErrorCodes.REG_T_ERROR_001;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", ex.getLocalizedMessage());
        List<ResponseWithValidationErrors> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ResponseWithValidationErrors responseWithValidationErrors = new ResponseWithValidationErrors();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            responseWithValidationErrors.setErrorCode(REG_F_ERROR_002.getCode());
            responseWithValidationErrors.setDescription(fieldName + " " + errorMessage);
            errors.add(responseWithValidationErrors);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(AuthenticationException ex) {
        log.error("AuthenticationException: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<Object> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        responseMessageWithError.setDescription(ex.getMessage());
        responseMessageWithError.setErrorCode(ex.getErrorCode());
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<Object> exception(InvalidUsernameException ex) {
        log.error("InvalidUsernameException: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        responseMessageWithError.setDescription(ex.getMessage());
        responseMessageWithError.setErrorCode(ex.getErrorCode());
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> exception(SQLException ex) {
        log.error("SQLException: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        responseMessageWithError.setErrorCode(REG_T_ERROR_001.getCode());
        responseMessageWithError.setDescription(ex.getLocalizedMessage());
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> exception(DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        responseMessageWithError.setErrorCode(REG_T_ERROR_001.getCode());
        responseMessageWithError.setDescription(ex.getLocalizedMessage());
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        log.error("Exception: {}", ex.getLocalizedMessage());
        ResponseMessageWithError responseMessageWithError = new ResponseMessageWithError();
        responseMessageWithError.setErrorCode(REG_T_ERROR_001.getCode());
        responseMessageWithError.setDescription(ex.getLocalizedMessage());
        return new ResponseEntity<>(responseMessageWithError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
