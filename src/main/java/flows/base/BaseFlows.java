package flows.base;

import configuration.handler.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Slf4j
public class BaseFlows {
    protected WebDriver driver;
    protected Random random;

    public BaseFlows() {
        this.driver = DriverManager.getDriver();
        this.random = new Random();
    }

    public <T extends BasePage> T at(Class<T> pageType) {
        log.debug("Initialising page: {}", pageType.getSimpleName());
        try {
            return pageType.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to initialize page: " + pageType.getSimpleName(), e);
        }
    }

    public <T extends BasePage> void at(Class<T> pageType, Consumer<T> pageAction) {
        log.debug("Initialising page: {}", pageType.getSimpleName());
        try {
            var page = pageType.getDeclaredConstructor().newInstance();
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
