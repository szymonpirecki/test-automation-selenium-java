package filterTests;

import model.basket.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product Filter")
public class FilterTest extends FilterBase {

    @Test
    @DisplayName("Should filter products by price range and restore full count after clearing filters")
    public void shouldFilterProductsByPriceRangeTest() {
        int productCountBeforeFilter = productSteps
                .navigateToCategory(testData.category())
                .getProductCount();

        List<Product> filteredProducts = filterSteps
                .applyPriceFilter(testData.minPrice(), testData.maxPrice())
                .getProducts();

        filterSteps.verifyProductsWithinPriceRange(filteredProducts, testData.minPrice(), testData.maxPrice());

        int productCountAfterClear = filterSteps
                .clearFilters()
                .getProductCount();

        assertThat(productCountBeforeFilter).isEqualTo(productCountAfterClear);
    }
}
