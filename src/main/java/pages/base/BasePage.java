package pages.base;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

@Slf4j
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait defaultWait;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        init(driver);
        PageFactory.initElements(driver, this);
    }

    public BasePage(WebDriver driver, WebElement parent) {
        init(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(parent), this);
    }

    private void init(WebDriver driver) {
        this.driver = driver;
        defaultWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(System.getProperty("explicitWaitTimeout"))));
        actions = new Actions(driver);
    }


    public void click(WebElement button) {
        defaultWait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    public void waitForElement(WebElement element) {
        defaultWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForAllElements(List<WebElement> elements) {
        defaultWait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void sendKeys(WebElement input, String text) {
        waitForElement(input);
        input.click();
        input.clear();
        input.sendKeys(text);
    }

    @SneakyThrows
    public BigDecimal getBigDecimal(WebElement element) {
        waitForElement(element);
        String priceString = element.getText().replace(System.getProperty("currency"), "");
        NumberFormat format = NumberFormat.getNumberInstance(new Locale(System.getProperty("market")));
        Number number = format.parse(priceString);
        return new BigDecimal(number.toString());
    }

    public int getInt(WebElement webElement) {
        waitForElement(webElement);
        return Integer.parseInt(webElement.getText().replaceAll("[^0-9]", ""));
    }

    public int getValue(WebElement webElement) {
        waitForElement(webElement);
        return Integer.parseInt(webElement.getAttribute("value"));
    }

    public void selectByVisibleText(WebElement selectElement, String text) {
        waitForElement(selectElement);
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
    }

    public void selectRadioButton(WebElement radioBtn) {
        if (radioBtn.isSelected()) {
            return;
        }
        radioBtn.click();
    }
}