package pages.login;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

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
    }
}
