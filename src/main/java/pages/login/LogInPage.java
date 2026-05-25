package pages.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;

public class LogInPage extends BasePage {
    public LogInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".login-form input[type='email']")
    private WebElement emailInput;

    @FindBy(css = ".login-form input[type='password']")
    private WebElement passwordInput;

    @FindBy(css = "#submit-login")
    private WebElement signInBtn;

    public void logIn(String email, String password) {
        sendKeys(emailInput, email);
        sendKeys(passwordInput, password);
        click(signInBtn);
        verifyLoginSucceeded(email);
    }

    private void verifyLoginSucceeded(String email) {
        boolean loginFailed = defaultWait.until(driver ->
                driver.getCurrentUrl().contains("/my-account") ||
                driver.findElements(By.cssSelector(".alert-danger")).stream()
                        .anyMatch(WebElement::isDisplayed)
        );
        if (!driver.getCurrentUrl().contains("/my-account")) {
            String errorText = driver.findElements(By.cssSelector(".alert-danger")).stream()
                    .filter(WebElement::isDisplayed)
                    .map(WebElement::getText)
                    .findFirst()
                    .orElse("unknown error");
            throw new IllegalStateException(
                    "Login failed for user [" + email + "]. Page error: " + errorText);
        }
    }
}
