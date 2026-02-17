package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
  private static final Properties properties = new Properties();

  static {
    try (InputStream input =
                 TestConfig.class.getClassLoader()
                         .getResourceAsStream("config.properties")) {

      properties.load(input);

    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public static String get(String key) {
    return System.getProperty(key, properties.getProperty(key));
  }
}
