package nl.com.acs.common.exception;
public enum FunctionalErrorCodes {
    REG_F_ERROR_001("REF-F-ERROR-001"),//Username already taken Exception
    REG_F_ERROR_002("REF-F-ERROR-002"), // Input Validation Exception
    REG_F_ERROR_003("REF-F-ERROR-003"), // Invalid username and password
    REG_A_ERROR_002("REF-A-ERROR-001"); // Authentication Exception

    private final String code;

    /**
     * Constructor.
     *
     * @param code
     *         the error code.
     */
    FunctionalErrorCodes(final String code) {
        this.code = code;
    }

    /**
     * get the code.
     *
     * @return the action code.
     */
    public String getCode() {
        return code;
    }
}
