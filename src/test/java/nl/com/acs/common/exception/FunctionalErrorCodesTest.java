package nl.com.acs.common.exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalErrorCodesTest {


    @Test
    void getCode() {
        /* Act and Assert */
        assertEquals("REF-F-ERROR-001", FunctionalErrorCodes.REG_F_ERROR_001.getCode());
        assertEquals("REF-F-ERROR-002", FunctionalErrorCodes.REG_F_ERROR_002.getCode());
        assertEquals("REF-F-ERROR-003", FunctionalErrorCodes.REG_F_ERROR_003.getCode());
        assertEquals("REF-A-ERROR-001", FunctionalErrorCodes.REG_A_ERROR_002.getCode());
    }
}
