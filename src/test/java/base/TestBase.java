package base;

import configuration.handler.BrowserHandler;
import configuration.handler.DriverManager;
import configuration.handler.EnvironmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import providers.UrlProvider;

@Slf4j
public class TestBase {

    @BeforeAll
    static void beforeAll() {
        EnvironmentHandler.setEnvironmentProperties();
        BrowserHandler.setBrowserProperties();
    }

    @BeforeEach
    void setUp() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(UrlProvider.HOME_URL.getUrl());
    }

    @AfterEach
    void tearDown() {
        DriverManager.quit();
    }
}