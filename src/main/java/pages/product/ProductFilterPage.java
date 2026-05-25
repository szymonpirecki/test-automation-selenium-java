package pages.product;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;

import java.math.BigDecimal;
import java.util.Arrays;


@Getter
@Slf4j
public class ProductFilterPage extends BasePage {

    public ProductFilterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#search_filters")
    private WebElement productFilterTable;

    @FindBy(css = ".ui-slider-handle:first-of-type")
    private WebElement priceSliderHandleLeft;

    @FindBy(css = ".ui-slider-handle:last-of-type")
    private WebElement priceSliderHandleRight;

    @FindBy(css = "[data-slider-label='Price'] p")
    private WebElement priceScope;

    @FindBy(css = "#_desktop_search_filters_clear_all > button")
    private WebElement clearFilterBtn;

    public enum SliderType {
        LEFT, RIGHT
    }

    public void waitForFilterReload() {
        defaultWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".faceted-overlay")));
    }

    public void clearAllFilters() {
        click(clearFilterBtn);
        waitForFilterReload();
    }

    public boolean isFilterPanelDisplayed() {
        log.debug("Verifying filter panel is visible");
        return productFilterTable.isDisplayed();
    }

    public void adjustSliderHandle(SliderType sliderType, BigDecimal currentValue, BigDecimal desiredValue) {
        WebElement sliderHandle = (sliderType.equals(SliderType.LEFT)) ? priceSliderHandleLeft : priceSliderHandleRight;

        int steps = calculateSliderSteps(currentValue, desiredValue);
        if (steps == 0) return;

        Keys direction = resolveSliderDirection(currentValue, desiredValue);
        for (int i = 0; i < steps; i++) {
            waitForElement(priceScope);
            sliderHandle.sendKeys(direction);
            waitForFilterReload();
            log.debug("Slider step {}/{}", i + 1, steps);
        }
        log.debug("Slider adjusted {} step(s) in {} direction", steps, direction);
    }


    private BigDecimal[] readPriceRangeFromSlider() {
        waitForElement(priceScope);
        String[] values = priceScope.getText().split("-");
        return Arrays.stream(values)
                .map(p -> p.replaceAll("[^0-9.]", ""))
                .map(BigDecimal::new)
                .toArray(BigDecimal[]::new);
    }

    public BigDecimal getCurrentMinPrice() {
        return readPriceRangeFromSlider()[0];
    }

    public BigDecimal getCurrentMaxPrice() {
        return readPriceRangeFromSlider()[1];
    }

    private Keys resolveSliderDirection(BigDecimal currentValue, BigDecimal desiredValue) {
        return currentValue.compareTo(desiredValue) < 0 ? Keys.RIGHT : Keys.LEFT;
    }

    private int calculateSliderSteps(BigDecimal currentValue, BigDecimal desiredValue) {
        return currentValue.subtract(desiredValue).abs().intValue();
    }
}