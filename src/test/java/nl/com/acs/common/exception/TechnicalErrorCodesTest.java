package nl.com.acs.common.exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnicalErrorCodesTest {
    @Test
    void getCode() {
        /* Act and Assert */
        assertEquals("REF-T-ERROR-001", TechnicalErrorCodes.REG_T_ERROR_001.getCode());
    }

}
