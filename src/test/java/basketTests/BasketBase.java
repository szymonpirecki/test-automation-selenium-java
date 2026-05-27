package basketTests;

import base.TestBase;
import steps.basket.BasketSteps;
import steps.product.ProductSteps;
import model.testdata.BasketTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class BasketBase extends TestBase {

    protected final BasketTestData testData = TestDataProvider.basketTestData();

    BasketSteps basketSteps;
    ProductSteps productSteps;

    @BeforeEach
    public void setUpBasket() {
        productSteps = new ProductSteps();
        basketSteps = new BasketSteps();
    }
}
