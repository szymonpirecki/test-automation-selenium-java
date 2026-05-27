package filterTests;

import base.TestBase;
import steps.product.FilterSteps;
import steps.product.ProductSteps;
import lombok.extern.slf4j.Slf4j;
import model.testdata.FilterTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

@Slf4j
public class FilterBase extends TestBase {

    protected final FilterTestData testData = TestDataProvider.filterTestData();

    FilterSteps filterSteps;
    ProductSteps productSteps;

    @BeforeEach
    public void init() {
        filterSteps = new FilterSteps();
        productSteps = new ProductSteps();
    }
}
