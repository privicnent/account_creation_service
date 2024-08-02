package nl.com.acs.common.security;
import jakarta.servlet.http.HttpServletRequest;
import nl.com.acs.common.exception.AuthenticationException;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static nl.com.acs.common.exception.FunctionalErrorCodes.REG_A_ERROR_002;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class BasicAuthorizationMethodInterceptorTest {
    private HttpServletRequest httpServletRequest;
    private MethodInvocation methodInvocation;
    private BasicAuthorizationMethodInterceptor basicAuthorizationMethodInterceptor;


    @BeforeEach
    void setUp() {
        methodInvocation=mock(MethodInvocation.class);
        httpServletRequest=mock(HttpServletRequest.class);
        basicAuthorizationMethodInterceptor=new BasicAuthorizationMethodInterceptor(httpServletRequest);
        ReflectionTestUtils.setField(basicAuthorizationMethodInterceptor, "basicAuthUserName", "username");
        ReflectionTestUtils.setField(basicAuthorizationMethodInterceptor, "basicAuthPassword", "password");
    }

    @Test
    void invoke_withValidAuthorization() throws Throwable {
        /* Arrange */
        String username = "username";
        String password = "password";
        String basicAuth = "Basic " + new String(
                Base64.getEncoder().encode((username + ":" + password).getBytes(StandardCharsets.UTF_8)));

        when(httpServletRequest.getHeader("Authorization")).thenReturn(basicAuth);

        /* Act */
        basicAuthorizationMethodInterceptor.invoke(methodInvocation);
    }

    @Test
    void invoke_withInvalidAuthorization() {
        /* Arrange */
        String username = "wrong_username";
        String password = "wrong_password";
        String basicAuth = "Basic " + new String(
                Base64.getEncoder().encode((username + ":" + password).getBytes(StandardCharsets.UTF_8)));

        when(httpServletRequest.getHeader("Authorization")).thenReturn(basicAuth);

        /* Act */
        AuthenticationException authenticationException=assertThrows(AuthenticationException.class, () -> {
            basicAuthorizationMethodInterceptor.invoke(methodInvocation);
        });

        /* Assert */
        assertEquals("Basic Authentication Failed", authenticationException.getMessage());
        assertEquals(REG_A_ERROR_002.getCode(), authenticationException.getErrorCode());
    }
}
