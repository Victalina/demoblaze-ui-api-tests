package tests.ui;

import api.AuthApi;
import api.SignUpApi;
import com.codeborne.selenide.Configuration;
import config.TestConfig;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

  protected final AuthApi authApi = new AuthApi();
  protected final SignUpApi signUpApi = new SignUpApi();

  @BeforeAll
  static void setUp() {

    RestAssured.baseURI = TestConfig.get("base.url.api");
    Configuration.baseUrl = TestConfig.get("base.url.ui");
  }

  @AfterEach
  void addAttachments() {
    Attach.screenshotAs("Last screenshot");
    Attach.pageSource();
    Attach.browserConsoleLogs();

    closeWebDriver();
  }
}
