package tests.api;

import api.AuthApi;
import api.CatalogApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

  protected final CatalogApi catalogApi = new CatalogApi();
  protected final AuthApi authApi = new AuthApi();

  @BeforeAll
  static void setupConfig() {
    RestAssured.baseURI = "https://api.demoblaze.com";
  }

}
