package checkoutTests;

import base.TestBase;
import flows.basket.BasketFlows;
import flows.checkout.CheckoutFlows;
import flows.product.ProductFlows;
import flows.user.AccountFlows;
import flows.user.LoginFlows;
import model.testdata.CheckoutTestData;
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
        loginFlows = new LoginFlows(driver);
        productFlows = new ProductFlows(driver);
        accountFlows = new AccountFlows(driver);
        basketFlows = new BasketFlows(driver);
        checkoutFlows = new CheckoutFlows(driver);
    }
}
