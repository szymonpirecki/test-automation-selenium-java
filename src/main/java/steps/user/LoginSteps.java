package steps.user;

import steps.base.BaseSteps;
import pages.login.LogInPage;
import providers.UrlProvider;

public class LoginSteps extends BaseSteps {

    public LoginSteps navigateToLoginPage() {
        driver.get(UrlProvider.LOGIN_URL.getUrl());
        return this;
    }

    public AccountSteps loginAs(String email, String password) {
        at(LogInPage.class).logIn(email, password);
        return new AccountSteps();
    }
}
