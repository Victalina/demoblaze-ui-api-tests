package tests.api;

import api.CatalogApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

  protected final CatalogApi catalogApi = new CatalogApi();

  @BeforeAll
  static void setupConfig() {
    RestAssured.baseURI = "https://api.demoblaze.com";
  }

}
