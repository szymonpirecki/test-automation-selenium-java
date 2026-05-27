package steps.product;

import steps.base.BaseSteps;
import lombok.extern.slf4j.Slf4j;
import model.basket.Product;
import pages.product.ProductFilterPage;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class FilterSteps extends BaseSteps {

    public ProductSteps clearFilters() {
        at(ProductFilterPage.class).clearAllFilters();
        return new ProductSteps();
    }

    public ProductSteps applyPriceFilter(BigDecimal minPrice, BigDecimal maxPrice) {
        at(ProductFilterPage.class, filterPage -> {
            filterPage.adjustSliderHandle(ProductFilterPage.SliderType.LEFT, filterPage.getCurrentMinPrice(), minPrice);
            filterPage.adjustSliderHandle(ProductFilterPage.SliderType.RIGHT, filterPage.getCurrentMaxPrice(), maxPrice);
        });
        return new ProductSteps();
    }

    // Assertions
    public void verifyProductsWithinPriceRange(List<Product> products, BigDecimal minPrice, BigDecimal maxPrice) {
        assertThat(products)
                .withFailMessage("No products found in the given price range")
                .isNotEmpty();
        products.forEach(product -> assertThat(product.getPrice())
                .withFailMessage("Product '%s' price is outside the expected range [%s, %s]", product.getName(), minPrice, maxPrice)
                .isGreaterThanOrEqualTo(minPrice)
                .isLessThanOrEqualTo(maxPrice));
    }
}
