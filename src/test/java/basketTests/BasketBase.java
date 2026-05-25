package basketTests;

import base.TestBase;
import flows.basket.BasketFlows;
import flows.product.ProductFlows;
import model.testdata.BasketTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class BasketBase extends TestBase {

    protected final BasketTestData testData = TestDataProvider.basketTestData();

    BasketFlows basketFlows;
    ProductFlows productFlows;

    @BeforeEach
    public void setUpBasket() {
        productFlows = new ProductFlows();
        basketFlows = new BasketFlows();
    }
}
