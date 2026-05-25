package flows.base;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Slf4j
public class BaseFlows {
    protected Random random;
    protected WebDriver driver;

    public BaseFlows(WebDriver driver) {
        this.driver = driver;
        this.random = new Random();
    }

    public <T extends BasePage> T at(Class<T> pageType) {
        log.debug("Initialising page: {}", pageType.getSimpleName());
        try {
            return pageType.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to initialize page: " + pageType.getSimpleName(), e);
        }
    }

    public <T extends BasePage> void at(Class<T> pageType, Consumer<T> pageAction) {
        log.debug("Initialising page: {}", pageType.getSimpleName());
        try {
            var page = pageType.getDeclaredConstructor(WebDriver.class).newInstance(driver);
            pageAction.accept(page);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to initialize page: " + pageType.getSimpleName(), e);
        }
    }

    public <T> T getRandom(List<T> elements) {
        if (elements.isEmpty()) {
            log.error("Cannot select random element — list is empty");
            return null;
        }
        return elements.get(random.nextInt(elements.size()));
    }
}
