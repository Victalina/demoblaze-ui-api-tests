package tests.ui;

import com.codeborne.selenide.Configuration;
import config.TestConfig;
import helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

  @BeforeAll
  static void setUp() {
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
