package nl.com.acs.register;
import nl.com.acs.common.entity.Account;
import nl.com.acs.common.entity.Address;
import nl.com.acs.common.entity.Customer;
import nl.com.acs.common.entity.Document;
import nl.com.acs.common.repository.AccountRepository;
import nl.com.acs.common.repository.AddressRepository;
import nl.com.acs.common.repository.CustomerRepository;
import nl.com.acs.common.repository.DocumentRepository;
import nl.com.acs.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class RegisterSaveServiceTest {

    private RegisterSaveService registerSaveService;

    private DocumentRepository documentRepository;
    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        documentRepository = mock(DocumentRepository.class);
        addressRepository = mock(AddressRepository.class);
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
        registerSaveService =
                new RegisterSaveService(documentRepository, addressRepository, customerRepository, accountRepository);
    }

    @Test
    void saveAll_withValidRequest() {
        /* Arrange */
        RegisterRequest registerRequest = creteRegisterRequest();

        Document document = new Document(registerRequest);
        Address address = new Address(registerRequest);
        Customer customer = new Customer(registerRequest);
        customer.setDocument(document);
        customer.setAddress(address);
        Account account = new Account();
        account.setCustomer(customer);

        when(documentRepository.save(any(Document.class))).thenReturn(document);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        /* Act */
        Account savedAccount = registerSaveService.saveAll(registerRequest);

        /* Assert */
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(captor.capture());
        Account accountResult = captor.getValue();

        assertNotNull(accountResult.getCustomer().getCustomerId());
        assertEquals("TestGivenName", accountResult.getCustomer().getGivenName());
        assertEquals("J", accountResult.getCustomer().getNameInitial());
        assertEquals("TestSurname", accountResult.getCustomer().getSurname());
        assertEquals("TestUserName", accountResult.getCustomer().getUserName());
        //assertNotNull(accountResult.getCustomer().getPassword());
        assertNotNull(accountResult.getCustomer().getAddress().getAddressId());
        assertEquals("TestStreet", accountResult.getCustomer().getAddress().getStreet());
        assertEquals(123, accountResult.getCustomer().getAddress().getHouseNumber());
        assertEquals("TestCity", accountResult.getCustomer().getAddress().getCity());
        assertEquals("TestCountry", accountResult.getCustomer().getAddress().getCountry());
        assertEquals("1234 AB", accountResult.getCustomer().getAddress().getPostalCode());
        assertNotNull(accountResult.getCustomer().getDocument().getDocumentId());
        assertEquals("123ABC", accountResult.getCustomer().getDocument().getDocumentNumber());
        assertNotNull(accountResult.getCustomer().getDocument().getDocumentIssueDate());
        assertEquals("PASSPORT", accountResult.getCustomer().getDocument().getDocumentTypeCode());
        assertEquals("TestCountry", accountResult.getCustomer().getDocument().getDocumentIssueCountry());
        assertNotNull(accountResult.getAccountId());
        assertNotNull(accountResult.getIban());

        assertNotNull(savedAccount);
        assertEquals("TestUserName", savedAccount.getCustomer().getUserName());
        verify(documentRepository, times(1)).save(any(Document.class));
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void isUsernameTaken_withExistingUsername() {
        /* Arrange */
        String username = "UserName";
        Customer customer = new Customer();
        customer.setUserName(username);

        when(customerRepository.findByUserName(username)).thenReturn(Optional.of(customer));

        /* Act */
        boolean isUsernameTaken = registerSaveService.isUsernameTaken(username);

        /* Assert */
        assertTrue(isUsernameTaken);
    }

    @Test
    void isUsernameTaken_withNonExistingUsername() {
        /* Arrange */
        String username = "UserName";

        when(customerRepository.findByUserName(username)).thenReturn(Optional.empty());

        /* Act */
        boolean isUsernameTaken = registerSaveService.isUsernameTaken(username);

        /* Assert */
        assertFalse(isUsernameTaken);
    }

    private RegisterRequest creteRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setGivenName("TestGivenName");
        registerRequest.setNameInitial("J");
        registerRequest.setSurname("TestSurname");
        registerRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        registerRequest.setUserName("TestUserName");
        registerRequest.setAddress(createAddress()); // assuming Address is valid
        registerRequest.setDocument(createDocument()); // assuming Document is valid
        return registerRequest;
    }

    private nl.com.acs.model.Address createAddress() {
        nl.com.acs.model.Address address = new nl.com.acs.model.Address();
        address.setStreet("TestStreet");
        address.setHouseNumber(123);
        address.setPostalCode("1234 AB");
        address.setCity("TestCity");
        address.setCountry("TestCountry");
        return address;
    }

    private nl.com.acs.model.Document createDocument() {
        nl.com.acs.model.Document document = new nl.com.acs.model.Document();
        document.setDocumentTypeCode(nl.com.acs.model.Document.DocumentTypeCodeEnum.PASSPORT);
        document.setDocumentNumber("123ABC");
        document.setDocumentIssueDate(LocalDate.of(2022, 1, 1));
        document.setDocumentIssueCountry("TestCountry");
        return document;
    }
}
