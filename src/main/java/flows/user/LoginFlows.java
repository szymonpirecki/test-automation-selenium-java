package flows.user;

import flows.base.BaseFlows;
import pages.login.LogInPage;
import providers.UrlProvider;

public class LoginFlows extends BaseFlows {

    public LoginFlows navigateToLoginPage() {
        driver.get(UrlProvider.LOGIN_URL.getUrl());
        return this;
    }

    public AccountFlows loginAs(String email, String password) {
        at(LogInPage.class).logIn(email, password);
        return new AccountFlows();
    }
}
