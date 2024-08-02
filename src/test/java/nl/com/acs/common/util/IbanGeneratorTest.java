package nl.com.acs.common.util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IbanGeneratorTest {

    @Test
    void generateIban_withValidFormat() {
        /* Act */
        String iban = IbanGenerator.generateIban();

        /* Assert */
        assertEquals(20, iban.length());
        assertTrue(iban.startsWith("NL"));
        assertTrue(iban.substring(4).matches("\\d{16}")); // checks if the rest of the string contains exactly 16 digits
    }
}
