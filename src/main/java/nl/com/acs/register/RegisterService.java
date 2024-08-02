package nl.com.acs.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.entity.Account;
import nl.com.acs.common.exception.UsernameAlreadyTakenException;
import org.springframework.stereotype.Service;
import nl.com.acs.model.RegisterRequest;
import nl.com.acs.model.RegisterResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final RegisterSaveService registerSaveService;

    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("RegisterService.registerService start");

        if (registerSaveService.isUsernameTaken(registerRequest.getUserName())){
            log.error("RegisterService.registerService - Username already taken");
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        Account account = registerSaveService.saveAll(registerRequest);
        log.info("RegisterService.registerService End");
        return constructRegisterResponse(account);
    }

    private RegisterResponse constructRegisterResponse(Account account) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserName(account.getCustomer().getUserName());
        registerResponse.setPassword(account.getCustomer().getPassword());
        return registerResponse;
    }

}
