package flows.product;

import flows.base.BaseFlows;
import lombok.extern.slf4j.Slf4j;
import model.basket.Product;
import org.openqa.selenium.WebDriver;
import pages.home.HeaderPage;
import pages.product.CategoryPage;
import pages.product.ProductFilterPage;
import pages.product.ProductGridPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProductFlows extends BaseFlows {
    public ProductFlows(WebDriver driver) {
        super(driver);
    }

    public Product findProduct(String productName) {
        return at(ProductGridPage.class).findProduct(productName);
    }

    public ProductFlows navigateToCategory(String categoryName) {
        at(HeaderPage.class).navigateToCategory(categoryName);
        return this;
    }

    public List<String> getCategoryNames() {
        return at(HeaderPage.class).getCategoryNames();
    }

    public List<Product> getProducts() {
        return at(ProductGridPage.class).getProducts();
    }

    public int getProductCount() {
        return at(ProductGridPage.class).getProductCount();
    }

    public Product getRandomProduct() {
        return getRandom(at(ProductGridPage.class).getProducts());
    }

    public int getRandomQuantity() {
        return random.nextInt(5) + 1;
    }

    // Assertions
    public void verifySubCategoryDetails(String categoryName) {
        log.debug("Validating subcategories for: {}", categoryName);
        navigateToCategory(categoryName);

        if (!at(CategoryPage.class).hasSubCategories()) {
            return;
        }

        for (String subCategoryName : at(CategoryPage.class).getSubCategoryNames()) {
            log.debug("Validating subcategory: {}", subCategoryName);
            at(CategoryPage.class).navigateToSubCategory(subCategoryName);
            verifyCategoryPage(subCategoryName);
            driver.navigate().back();
        }
    }

    public void verifyCategoryDetails(String categoryName) {
        log.debug("Validating category: {}", categoryName);
        navigateToCategory(categoryName);
        verifyCategoryPage(categoryName);
    }

    private void verifyCategoryPage(String categoryName) {
        var categoryTitle = at(CategoryPage.class).getCategoryTitle();
        var productCountLabel = at(CategoryPage.class).getTotalProductCount();
        var productCount = at(ProductGridPage.class).getProductCount();

        log.debug("Category: {}\nTitle: {}\nLabel count: {}\nDisplayed count: {}",
                categoryName, categoryTitle, productCountLabel, productCount);

        assertThat(categoryTitle).isEqualToIgnoringCase(categoryName);
        assertThat(productCountLabel).isEqualTo(productCount);
        assertThat(at(ProductFilterPage.class).isFilterPanelDisplayed()).isTrue();
    }
}
