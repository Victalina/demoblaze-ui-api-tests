package tests.ui;

import api.AuthApi;
import api.CartApi;
import api.CatalogApi;
import api.SignUpApi;
import com.codeborne.selenide.Configuration;
import config.TestConfig;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

  protected final AuthApi authApi = new AuthApi();
  protected final SignUpApi signUpApi = new SignUpApi();
  protected final CatalogApi catalogApi = new CatalogApi();
  protected final CartApi cartApi = new CartApi();

  @BeforeAll
  static void setUp() {

    RestAssured.baseURI = TestConfig.get("base.url.api");
    Configuration.baseUrl = TestConfig.get("base.url.ui");
    Configuration.timeout = 8000;
    Configuration.browser = System.getProperty("browser", "chrome");
    Configuration.browserVersion = System.getProperty("version", "128");
    Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
    Configuration.remote = System.getProperty("remoteBrowser");

    if (System.getProperty("slowEnv") != null) {
      Configuration.timeout = 30000;
    }

    if (Configuration.remote != null) {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("selenoid:options", Map.<String, Object>of(
              "enableVNC", true,
              "enableVideo", true
      ));
      Configuration.browserCapabilities = capabilities;
    }
  }

  @AfterEach
  void addAttachments() {
    Attach.screenshotAs("Last screenshot");
    Attach.pageSource();
    Attach.browserConsoleLogs();

    if (Configuration.remote != null) {
      Attach.addVideo();
    }
    closeWebDriver();
  }
}
