package checkoutTests;

import base.TestBase;
import steps.basket.BasketSteps;
import steps.checkout.CheckoutSteps;
import steps.product.ProductSteps;
import steps.user.AccountSteps;
import steps.user.LoginSteps;
import model.testdata.CheckoutTestData;
import model.testdata.Credentials;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class CheckoutBase extends TestBase {

    protected final CheckoutTestData testData = TestDataProvider.checkoutTestData();

    LoginSteps loginSteps;
    ProductSteps productSteps;
    AccountSteps accountSteps;
    BasketSteps basketSteps;
    CheckoutSteps checkoutSteps;

    @BeforeEach
    public void setUpCheckout() {
        Credentials credentials = testData.credentials();
        if (credentials.email().isBlank() || credentials.password().isBlank()) {
            throw new IllegalStateException(
                    "Checkout test requires credentials. Set TEST_USER_EMAIL and TEST_USER_PASSWORD env vars " +
                    "(or GitHub Actions secrets). Current values — email blank: " + credentials.email().isBlank() +
                    ", password blank: " + credentials.password().isBlank());
        }
        loginSteps = new LoginSteps();
        productSteps = new ProductSteps();
        accountSteps = new AccountSteps();
        basketSteps = new BasketSteps();
        checkoutSteps = new CheckoutSteps();
    }
}
