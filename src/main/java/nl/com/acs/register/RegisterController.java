package nl.com.acs.register;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.security.BasicAuthorization;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import nl.com.acs.api.RegistrationApi;
import nl.com.acs.model.RegisterResponse;
import nl.com.acs.model.RegisterRequest;

@RestController
@AllArgsConstructor
@Slf4j
public class RegisterController implements RegistrationApi {
    private final RegisterService registerService;

    @BasicAuthorization
    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) {
        log.info("RegisterController.register - registerRequest: {}", registerRequest);
        RegisterResponse registerResponse = registerService.register(registerRequest);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).cacheControl(CacheControl.noCache())
                .body(registerResponse);
    }
}
