package context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class TestUsers {

  private static final Properties properties = new Properties();

  static {
    try (InputStream input =
                 TestUsers.class.getClassLoader()
                         .getResourceAsStream("test-users.properties")) {

      if (input != null) {
        properties.load(input);
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public static Map<String, String> getUsersMap() {
    return properties.entrySet().stream()
            .collect(Collectors.toMap(
                    key -> key.getKey().toString(),
                    value -> value.getValue().toString(),
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new
            ));
  }
}