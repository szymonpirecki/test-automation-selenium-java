package base;

import configuration.handler.BrowserHandler;
import configuration.handler.DriverManager;
import configuration.handler.EnvironmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import providers.UrlProvider;
import utils.ScreenshotUtil;

@Slf4j
public class TestBase {

    @RegisterExtension
    static final TestWatcher failureWatcher = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            WebDriver driver = DriverManager.getDriverOrNull();
            if (driver == null) {
                log.error("[FAILED] {} — driver unavailable, skipping screenshot | cause: {}",
                        context.getDisplayName(), cause.getMessage());
                return;
            }
            String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_");
            ScreenshotUtil.captureScreenshot(driver, testName, "FAILED");
            ScreenshotUtil.capturePageSource(driver, testName, "FAILED");
            log.error("[FAILED] {} | URL: {} | cause: {}",
                    testName, driver.getCurrentUrl(), cause.getMessage());
        }
    };

    @BeforeAll
    static void beforeAll() {
        EnvironmentHandler.setEnvironmentProperties();
        BrowserHandler.setBrowserProperties();
    }

    @BeforeEach
    void setUp() {
        log.info("[SETUP]  class={} thread={} id={}",
                getClass().getSimpleName(),
                Thread.currentThread().getName(),
                Thread.currentThread().getId());
        DriverManager.initDriver();
        DriverManager.getDriver().get(UrlProvider.HOME_URL.getUrl());
    }

    @AfterEach
    void tearDown() {
        DriverManager.quit();
        log.info("[TEARDOWN] class={} thread={} id={}",
                getClass().getSimpleName(),
                Thread.currentThread().getName(),
                Thread.currentThread().getId());
    }
}