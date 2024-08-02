package nl.com.acs.common.security;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.exception.AuthenticationException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
@RequiredArgsConstructor
public class BasicAuthorizationMethodInterceptor implements MethodInterceptor {

    private final HttpServletRequest httpServletRequest;

    @Value("${basic.auth.username}")
    private String basicAuthUserName;
    @Value("${basic.auth.password}")
    private String basicAuthPassword;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        invokeBasicAuthorizationCheck();
        return invocation.proceed();
    }

    private void invokeBasicAuthorizationCheck() {
        final String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
            log.error("Basic Authentication failed - header Empty");
            authenticationFailureException();
        }
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] credentialArray = credentials.split(":", 2);
        if (checkAppToAppIsNotAuthorized(credentialArray)) {
            authenticationFailureException();
        }
    }

    private boolean checkAppToAppIsNotAuthorized(String[] credentialArray) {
        return !(basicAuthUserName.equals(credentialArray[0]) && basicAuthPassword.equals(credentialArray[1]));
    }

    private void authenticationFailureException() {
        throw new AuthenticationException("Basic Authentication Failed", new RuntimeException());
    }
}
