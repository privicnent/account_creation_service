package nl.com.acs.common.exception;
public enum TechnicalErrorCodes {
    REG_T_ERROR_001("REF-T-ERROR-001"),
    REG_T_ERROR_002("REF-T-ERROR-002"); //Register service does not permit further calls

    private final String code;

    /**
     * Constructor.
     *
     * @param code
     *         the error code.
     */
    TechnicalErrorCodes(final String code) {
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
