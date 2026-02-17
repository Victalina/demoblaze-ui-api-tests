package tests.api;

import api.AuthApi;
import api.CartApi;
import api.CatalogApi;
import api.SignUpApi;
import config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

  protected final CatalogApi catalogApi = new CatalogApi();
  protected final AuthApi authApi = new AuthApi();
  protected final CartApi cartApi = new CartApi();
  protected final SignUpApi signUpApi = new SignUpApi();

  @BeforeAll
  static void setupConfig() {
    RestAssured.baseURI = TestConfig.get("base.url");
  }

}
