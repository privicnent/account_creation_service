package nl.com.acs.register;
import nl.com.acs.common.entity.Account;
import nl.com.acs.common.entity.Customer;
import nl.com.acs.common.exception.UsernameAlreadyTakenException;
import nl.com.acs.model.RegisterRequest;
import nl.com.acs.model.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_F_ERROR_001;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    private RegisterService registerService;
    private RegisterSaveService registerSaveService;

    @BeforeEach
    void setUp() {
        registerSaveService = mock(RegisterSaveService.class);
        registerService = new RegisterService(registerSaveService);
    }

    @Test
    void register_withValidRequest() {
        /* Arrange */
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("UserName");

        Account account = new Account();
        Customer customer = new Customer();
        customer.setUserName("UserName");
        customer.setPassword("Password");
        account.setCustomer(customer);

        when(registerSaveService.isUsernameTaken(registerRequest.getUserName())).thenReturn(false);
        when(registerSaveService.saveAll(registerRequest)).thenReturn(account);

        /* Act */
        RegisterResponse registerResponse = registerService.register(registerRequest);

        /* Assert */
        assertEquals("UserName", registerResponse.getUserName());
        assertEquals("Password", registerResponse.getPassword());
        verify(registerSaveService,times(1)).isUsernameTaken(registerRequest.getUserName());
        verify(registerSaveService,times(1)).saveAll(registerRequest);
    }

    @Test
    void register_withUsernameAlreadyTaken() {
        /* Arrange */
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("john_doe");

        when(registerSaveService.isUsernameTaken(registerRequest.getUserName())).thenReturn(true);

        /* Act */
        UsernameAlreadyTakenException usernameAlreadyTakenException =
                assertThrows(UsernameAlreadyTakenException.class, () -> {
                    registerService.register(registerRequest);
                });

        /* Assert */
        assertEquals("Username already taken", usernameAlreadyTakenException.getMessage());
        assertEquals(REG_F_ERROR_001.getCode(), usernameAlreadyTakenException.getErrorCode());
        verify(registerSaveService,times(1)).isUsernameTaken(registerRequest.getUserName());
    }
}
