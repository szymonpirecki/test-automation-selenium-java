package searchTests;

import base.TestBase;
import steps.product.ProductSteps;
import steps.product.SearchSteps;
import model.testdata.SearchTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class SearchBase extends TestBase {

    protected final SearchTestData testData = TestDataProvider.searchTestData();

    SearchSteps searchSteps;
    ProductSteps productSteps;

    @BeforeEach
    public void init() {
        searchSteps = new SearchSteps();
        productSteps = new ProductSteps();
    }
}
