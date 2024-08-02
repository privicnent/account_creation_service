package nl.com.acs.logon;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.com.acs.api.LoginApi;
import nl.com.acs.common.security.BasicAuthorization;
import nl.com.acs.model.UserLoginRequest;
import nl.com.acs.model.UserLoginResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogonController implements LoginApi {
    private final LogonService logonService;

    @BasicAuthorization
    @Override
    public ResponseEntity<UserLoginResponse> logon(UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).cacheControl(CacheControl.noCache())
                .body(logonService.logon(userLoginRequest));
    }
}
