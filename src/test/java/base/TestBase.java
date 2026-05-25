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