package searchTests;

import base.TestBase;
import flows.product.ProductFlows;
import flows.product.SearchFlows;
import model.testdata.SearchTestData;
import org.junit.jupiter.api.BeforeEach;
import providers.TestDataProvider;

public class SearchBase extends TestBase {

    protected final SearchTestData testData = TestDataProvider.searchTestData();

    SearchFlows searchFlows;
    ProductFlows productFlows;

    @BeforeEach
    public void init() {
        searchFlows = new SearchFlows();
        productFlows = new ProductFlows();
    }
}
