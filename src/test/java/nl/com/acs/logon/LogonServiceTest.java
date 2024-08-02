package nl.com.acs.logon;
import nl.com.acs.common.entity.Customer;
import nl.com.acs.common.exception.InvalidUsernameException;
import nl.com.acs.common.repository.CustomerRepository;
import nl.com.acs.model.UserLoginRequest;
import nl.com.acs.model.UserLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_F_ERROR_003;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogonServiceTest {

    private CustomerRepository customerRepository;

    private LogonService logonService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        logonService = new LogonService(customerRepository);
    }

    @Test
    void logon_withValidRequest() {
        /* Arrange */
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("john_doe");
        userLoginRequest.setPassword("password");

        Customer customer = new Customer();
        customer.setUserName("john_doe");
        customer.setPassword("password");

        when(customerRepository.findByUserName(userLoginRequest.getUserName())).thenReturn(Optional.of(customer));

        /* Act */
        UserLoginResponse userLoginResponse = logonService.logon(userLoginRequest);

        /* Assert */
        assertEquals("Success", userLoginResponse.getStatus());
    }

    @Test
    void logon_withInvalidUsername() {
        /* Arrange */
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("john_doe");
        userLoginRequest.setPassword("password");

        when(customerRepository.findByUserName(userLoginRequest.getUserName())).thenReturn(Optional.empty());

        /* Act */
        InvalidUsernameException invalidUsernameException = assertThrows(InvalidUsernameException.class, () -> {
            logonService.logon(userLoginRequest);
        });
        /* Assert */
        assertEquals("Invalid username", invalidUsernameException.getMessage());
        assertEquals(REG_F_ERROR_003.getCode(), invalidUsernameException.getErrorCode());
        verify(customerRepository,times(1)).findByUserName(userLoginRequest.getUserName());
    }

    @Test
    void logon_withInvalidPassword() {
        /* Arrange */
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("john_doe");
        userLoginRequest.setPassword("wrong_password");

        Customer customer = new Customer();
        customer.setUserName("john_doe");
        customer.setPassword("password");

        when(customerRepository.findByUserName(userLoginRequest.getUserName())).thenReturn(Optional.of(customer));

        /* Act */
        InvalidUsernameException invalidUsernameException = assertThrows(InvalidUsernameException.class, () -> {
            logonService.logon(userLoginRequest);
        });
        /* Assert */
        assertEquals("Invalid username or password", invalidUsernameException.getMessage());
        assertEquals(REG_F_ERROR_003.getCode(), invalidUsernameException.getErrorCode());
        verify(customerRepository,times(1)).findByUserName(userLoginRequest.getUserName());
    }
}
