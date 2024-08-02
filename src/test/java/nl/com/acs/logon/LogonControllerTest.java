package nl.com.acs.logon;
import nl.com.acs.model.UserLoginRequest;
import nl.com.acs.model.UserLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogonControllerTest {

    private LogonController logonController;
    private LogonService logonService;

    @BeforeEach
    void setUp() {
        logonService = mock(LogonService.class);
        logonController = new LogonController(logonService);
    }

    @Test
    void logon() {
        /* Arrange */
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setUserName("UserName");
        userLoginRequest.setPassword("Password");
        when(logonService.logon(userLoginRequest)).thenReturn(new UserLoginResponse().status("Success"));

        /* Act */
        ResponseEntity<UserLoginResponse> response = logonController.logon(userLoginRequest);

        /* Assert */
        verify(logonService).logon(userLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }
}
