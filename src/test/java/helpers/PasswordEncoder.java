package helpers;

import java.util.Base64;
import static java.nio.charset.StandardCharsets.UTF_8;

public class PasswordEncoder {

  public static String encode(String password) {
    return Base64.getEncoder().encodeToString(
            password.getBytes(UTF_8)
    );
  }
}

