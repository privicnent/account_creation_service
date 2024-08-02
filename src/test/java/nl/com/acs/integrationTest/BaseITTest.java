package nl.com.acs.integrationTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseITTest {
    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final int PORT = 8080;
    public static final String CONTEXT = "/account";
}
