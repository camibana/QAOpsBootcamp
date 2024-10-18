import br.com.automacao.specification.ApiRequestSepecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

public abstract class BaseTest {
    protected RequestSpecification serverestSpec;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "example.com"};

    @BeforeEach
    public void setUp() {
        serverestSpec = new ApiRequestSepecification().getUsuarios();
    }


    public static String gerarEmail(int length) {
        Random random = new Random();
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < length; i++) {
            email.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        email.append(random.nextInt(1000));
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];
        return email.toString() + "@" + domain;
    }
}
