package nl.com.acs.logon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.entity.Customer;
import nl.com.acs.common.exception.InvalidUsernameException;
import nl.com.acs.common.repository.CustomerRepository;
import nl.com.acs.model.UserLoginRequest;
import nl.com.acs.model.UserLoginResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogonService {

    private final CustomerRepository customerRepository;

    public UserLoginResponse logon(UserLoginRequest userLoginRequest) {
        log.info("LogonService.logon - userLoginRequest: {}", userLoginRequest.getUserName());
        Customer customer = findByUserName(userLoginRequest.getUserName()).orElseThrow(
                () -> new InvalidUsernameException("Invalid username"));

        validatePassword(customer, userLoginRequest.getPassword());

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.status("Success");
        return userLoginResponse;
    }

    private void validatePassword(Customer customer, String password) {
        if (!customer.getPassword().equals(password)) {
            throw new InvalidUsernameException("Invalid username or password");
        }
    }

    private Optional<Customer> findByUserName(String username) {
        return customerRepository.findByUserName(username);
    }
}
