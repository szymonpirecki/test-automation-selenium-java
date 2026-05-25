package filterTests;

import base.TestBase;
import flows.product.FilterFlows;
import flows.product.ProductFlows;
import lombok.extern.slf4j.Slf4j;
import model.testdata.FilterTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

@Slf4j
public class FilterBase extends TestBase {

    protected final FilterTestData testData = TestDataProvider.filterTestData();

    FilterFlows filterFlows;
    ProductFlows productFlows;

    @BeforeEach
    public void init() {
        filterFlows = new FilterFlows(driver);
        productFlows = new ProductFlows(driver);
    }
}
