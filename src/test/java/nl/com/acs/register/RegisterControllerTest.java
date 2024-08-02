package nl.com.acs.register;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import nl.com.acs.model.Address;
import nl.com.acs.model.Document;
import nl.com.acs.model.RegisterRequest;
import nl.com.acs.model.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {

    private RegisterController registerController;
    private RegisterService registerService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    void setUp() {
        registerService = mock(RegisterService.class);
        registerController = new RegisterController(registerService);
    }

    @Test
    void register() {
        /* Arrange */
        RegisterRequest registerRequest = creteRegisterRequest();
        RegisterResponse registerResponse = createRegisterResponse();
        when(registerService.register(registerRequest)).thenReturn(registerResponse);

        /* Act */
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        ResponseEntity<RegisterResponse> response = registerController.register(registerRequest);

        /* Assert */
        assertEquals(0, violations.size());
        verify(registerService).register(registerRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UserName", response.getBody().getUserName());
        assertEquals("Password", response.getBody().getPassword());
    }

    @Test
    void registerRequest_withInvalidSize_givenName() {
        /* Arrange */
        RegisterRequest registerRequest = createInvalidRegisterRequest("A", "A", "Surname", "UserName");

        /* Act */
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        /* Assert */
        assertEquals(1, violations.size());
        assertEquals("size must be between 2 and 50", violations.iterator().next().getMessage());
    }
    @Test
    void registerRequest_withNullValue_givenName() {
        /* Arrange */
        RegisterRequest registerRequest = createInvalidRegisterRequest(null, "A", "Surname", "UserName");

        /* Act */
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        /* Assert */
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void registerRequest_withInvalidCharacters_givenName() {
        /* Arrange */
        RegisterRequest registerRequest = createInvalidRegisterRequest("ABCD@", "A", "Surname", "UserName");

        /* Act */
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        /* Assert */
        assertEquals(1, violations.size());
        //assertEquals("must match \"^[a-zA-Z0-9]*$\"", violations.iterator().next().getMessage());
    }

    private RegisterRequest createInvalidRegisterRequest(String givenName, String nameInitial, String surname,
                                                         String userName)
    {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setGivenName(givenName);
        registerRequest.setNameInitial(nameInitial);
        registerRequest.setSurname(surname);
        registerRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        registerRequest.setUserName(userName);
        registerRequest.setAddress(createAddress()); // assuming Address is valid
        registerRequest.setDocument(createDocument()); // assuming Document is valid
        return registerRequest;
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

    private Address createAddress() {
        Address address = new Address();
        address.setStreet("TestStreet");
        address.setHouseNumber(123);
        address.setPostalCode("1234 AB");
        address.setCity("TestCity");
        address.setCountry("TestCountry");
        return address;
    }

    private Document createDocument() {
        Document document = new Document();
        document.setDocumentTypeCode(Document.DocumentTypeCodeEnum.PASSPORT);
        document.setDocumentNumber("123ABC");
        document.setDocumentIssueDate(LocalDate.of(2022, 1, 1));
        document.setDocumentIssueCountry("TestCountry");
        return document;
    }

    private RegisterResponse createRegisterResponse() {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserName("UserName");
        registerResponse.setPassword("Password");
        return registerResponse;
    }
}
