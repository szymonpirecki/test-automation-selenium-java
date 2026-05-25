package configuration.handler;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {}

    public static void initDriver() {
        WebDriver driver = new BrowserHandler().initDriver();
        driverHolder.set(driver);
        log.info("Driver initialized on thread [{}]", Thread.currentThread().getName());
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverHolder.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver not initialized for thread: " + Thread.currentThread().getName());
        }
        return driver;
    }

    public static void quit() {
        WebDriver driver = driverHolder.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverHolder.remove();
                log.info("Driver quit on thread [{}]", Thread.currentThread().getName());
            }
        }
    }
}
