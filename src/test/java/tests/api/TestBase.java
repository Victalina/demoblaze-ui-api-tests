package tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
  @BeforeAll
  static void setupConfig() {
    RestAssured.baseURI = "https://api.demoblaze.com";
  }
}
