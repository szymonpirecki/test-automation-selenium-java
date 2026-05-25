package checkoutTests;

import base.TestBase;
import flows.basket.BasketFlows;
import flows.checkout.CheckoutFlows;
import flows.product.ProductFlows;
import flows.user.AccountFlows;
import flows.user.LoginFlows;
import model.testdata.CheckoutTestData;
import model.testdata.Credentials;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class CheckoutBase extends TestBase {

    protected final CheckoutTestData testData = TestDataProvider.checkoutTestData();

    LoginFlows loginFlows;
    ProductFlows productFlows;
    AccountFlows accountFlows;
    BasketFlows basketFlows;
    CheckoutFlows checkoutFlows;

    @BeforeEach
    public void setUpCheckout() {
        Credentials credentials = testData.credentials();
        if (credentials.email().isBlank() || credentials.password().isBlank()) {
            throw new IllegalStateException(
                    "Checkout test requires credentials. Set TEST_USER_EMAIL and TEST_USER_PASSWORD env vars " +
                    "(or GitHub Actions secrets). Current values — email blank: " + credentials.email().isBlank() +
                    ", password blank: " + credentials.password().isBlank());
        }
        loginFlows = new LoginFlows();
        productFlows = new ProductFlows();
        accountFlows = new AccountFlows();
        basketFlows = new BasketFlows();
        checkoutFlows = new CheckoutFlows();
    }
}
