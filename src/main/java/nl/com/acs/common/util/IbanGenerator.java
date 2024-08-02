package nl.com.acs.common.util;
import java.util.Random;

public class IbanGenerator {
    private static final String[] COUNTRY_CODES = {"NL"};
    private static final Random RANDOM = new Random();

    public static String generateIban() {
        String countryCode = COUNTRY_CODES[RANDOM.nextInt(COUNTRY_CODES.length)];
        StringBuilder iban = new StringBuilder(countryCode + "00");
        for (int i = 0; i < 16; i++) {
            int digit = RANDOM.nextInt(10);
            iban.append(digit);
        }
        return iban.toString();
    }
}
