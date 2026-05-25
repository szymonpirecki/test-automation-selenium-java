package pages.login;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;
import utils.ScreenshotUtil;

@Slf4j
public class LogInPage extends BasePage {

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
        try {
            defaultWait.until(d ->
                    d.getCurrentUrl().contains("/my-account") ||
                    d.findElements(By.cssSelector(".alert-danger")).stream()
                            .anyMatch(WebElement::isDisplayed)
            );
        } catch (TimeoutException e) {
            log.error("Login timed out | URL: {}", driver.getCurrentUrl());
            ScreenshotUtil.captureScreenshot(driver, "login-verification", "TIMEOUT");
            ScreenshotUtil.capturePageSource(driver, "login-verification", "TIMEOUT");
            throw e;
        }
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
