package categoryTests;

import base.TestBase;
import steps.product.ProductSteps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public class CategoryBase extends TestBase {

    ProductSteps categorySteps;

    @BeforeEach
    public void init() {
        categorySteps = new ProductSteps();
    }
}
