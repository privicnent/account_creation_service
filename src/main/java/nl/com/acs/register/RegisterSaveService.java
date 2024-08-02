package nl.com.acs.register;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.acs.common.entity.Account;
import nl.com.acs.common.entity.Address;
import nl.com.acs.common.entity.Customer;
import nl.com.acs.common.entity.Document;
import nl.com.acs.common.exception.RegisterServiceException;
import nl.com.acs.common.repository.AccountRepository;
import nl.com.acs.common.repository.AddressRepository;
import nl.com.acs.common.repository.CustomerRepository;
import nl.com.acs.common.repository.DocumentRepository;
import nl.com.acs.model.RegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static nl.com.acs.common.util.IbanGenerator.generateIban;
import static nl.com.acs.common.util.PasswordGenerator.generatePassword;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterSaveService {

    private final DocumentRepository documentRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @RateLimiter(name = "register-service", fallbackMethod = "fallbackMethod")
    @Transactional
    public Account saveAll(RegisterRequest registerRequest) {
        Document document = saveDocument(registerRequest);
        Address address = saveAddress(registerRequest);
        Customer customer = saveCustomer(registerRequest, document, address);
        return saveAccount(customer);
    }

    private Document saveDocument(RegisterRequest registerRequest) {
        return documentRepository.save(new Document(registerRequest));
    }

    private Address saveAddress(RegisterRequest registerRequest) {
        return addressRepository.save(new Address(registerRequest));
    }

    private Customer saveCustomer(RegisterRequest registerRequest, Document document, Address address) {
        return customerRepository.save(constructCustomer(registerRequest, document, address));
    }

    private Account saveAccount(Customer customer) {
        return accountRepository.save(constructAccount(customer));
    }

    private Customer constructCustomer(RegisterRequest registerRequest, Document document, Address address) {
        Customer customer = new Customer(registerRequest);
        customer.setPassword(generatePassword(10));
        customer.setCreated(LocalDateTime.now());
        customer.setAddress(address);
        customer.setDocument(document);
        return customer;
    }

    private Account constructAccount(Customer customer) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setIban(generateIban());
        account.setAccountId(UUID.randomUUID().toString());
        return account;
    }

    public boolean isUsernameTaken(String username) {
        return customerRepository.findByUserName(username).isPresent();
    }

    private Account fallbackMethod(RequestNotPermitted requestNotPermitted) {
        log.error("Register service does not permit further calls Please try again later", requestNotPermitted);
        throw new RegisterServiceException("Register service does not permit further calls Please try again later");
    }
}
