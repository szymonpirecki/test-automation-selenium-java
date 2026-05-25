package filterTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product Filter")
public class FilterTest extends FilterBase {

    @Test
    @DisplayName("Should filter products by price range and restore full count after clearing filters")
    public void shouldFilterProductsByPriceRangeTest() {
        var productCountBeforeFilter = productFlows
                .navigateToCategory(testData.category())
                .getProductCount();

        var filteredProducts = filterFlows
                .applyPriceFilter(testData.minPrice(), testData.maxPrice())
                .getProducts();

        filterFlows.verifyProductsWithinPriceRange(filteredProducts, testData.minPrice(), testData.maxPrice());

        var productCountAfterClear = filterFlows
                .clearFilters()
                .getProductCount();

        assertThat(productCountBeforeFilter).isEqualTo(productCountAfterClear);
    }
}
