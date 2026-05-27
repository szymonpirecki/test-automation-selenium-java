package configuration.handler;

import configuration.model.YamlModel;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.util.Map;

@Slf4j
public class BrowserHandler {
    private String browserName = "chrome";
    private boolean browserHeadless = false;
    private int explicitWaitTimeout = 5;
    private boolean maximizeWindow = false;


    public static void setBrowserProperties() {
        log.info("Setting browser properties...");
        YamlModel yamlModel = YamlReader.getInstance().getYamlModel();
        Map<String, Object> browserSettingsMap = yamlModel.getBrowserSettings();
        for (Map.Entry<String, Object> entry : browserSettingsMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (System.getProperty(key) != null) {
                log.debug("Browser property '{}' already set via CLI — skipping YAML value '{}'", key, value);
                continue;
            }
            if (value != null) {
                System.setProperty(key, value.toString());
                log.debug("Browser property set: {} = {}", key, value);
            }
        }
        log.info("Browser properties set.");
    }

    public WebDriver initDriver() {
        adjustBrowserSettings();
        WebDriver driver = null;
        log.info("Initializing driver with browserName: {}", this.browserName);
        switch (this.browserName.toLowerCase()) {
            case "firefox" -> driver = getFirefoxDriver();
            default -> driver = getChromeDriver();
        }
        if (this.maximizeWindow)
            driver.manage().window().maximize();
        log.info("Driver initialized - Chosen browser: {}", this.browserName);
        return driver;
    }


    private void adjustBrowserSettings() {
        log.debug("Adjusting browser settings...");
        explicitWaitTimeout = isSpecified("explicitWaitTimeout") ?
                Integer.parseInt(System.getProperty("explicitWaitTimeout")) : this.explicitWaitTimeout;

        browserName = isSpecified("browserName") ?
                System.getProperty("browserName") : this.browserName;

        browserHeadless = isSpecified("browserHeadless") ?
                Boolean.parseBoolean(System.getProperty("browserHeadless")) : this.browserHeadless;

        maximizeWindow = isSpecified("maximizeWindow") ?
                Boolean.parseBoolean(System.getProperty("maximizeWindow")) : this.maximizeWindow;
        log.debug("Browser settings adjusted.");
    }

    private boolean isSpecified(String setting) {
        return (System.getProperty(setting) != null);
    }



    private WebDriver getFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        log.debug("Setting headless option to {}", this.browserHeadless);
        if (this.browserHeadless) options.addArguments("--headless");
        return new FirefoxDriver(options);
    }

    // The snap launcher symlink (/snap/bin/chromium.chromedriver) cannot be spawned
    // by Java's ProcessBuilder — it requires the shell environment that snap sets up.
    // The actual binary inside the snap mount works directly.
    private static final String SNAP_CHROMEDRIVER = "/snap/chromium/current/usr/lib/chromium-browser/chromedriver";

    private WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        log.debug("Setting headless option to {}", this.browserHeadless);
        if (this.browserHeadless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        File snapDriver = new File(SNAP_CHROMEDRIVER);
        if (snapDriver.exists()) {
            log.info("Using snap chromedriver: {}", SNAP_CHROMEDRIVER);
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(snapDriver)
                    .build();
            return new ChromeDriver(service, options);
        }
        return new ChromeDriver(options);
    }
}