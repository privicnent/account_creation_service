package nl.com.acs.common.util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void generatePassword_withValidLength() {
        /* Arrange */
        int length = 10;

        /* Act */
        String password = PasswordGenerator.generatePassword(length);

        /* Assert */
        assertEquals(length, password.length());
        assertTrue(password.matches("^[0-9A-Za-z]*$"));
    }
}
