package categoryTests;

import base.TestBase;
import flows.product.ProductFlows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public class CategoryBase extends TestBase {

    ProductFlows categoryFlows;

    @BeforeEach
    public void init() {
        categoryFlows = new ProductFlows(driver);
    }
}
