package pages.product;

import lombok.extern.slf4j.Slf4j;
import model.basket.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ProductGridPage extends BasePage {
    public ProductGridPage(WebDriver driver) {
        super(driver);
        waitForAllElements(productsOnGrid);
    }

    @FindBy(css = ".product")
    private List<WebElement> productsOnGrid;

    public int getProductCount() {
        return productsOnGrid.size();
    }

    public List<ProductMiniatureComponent> getProductCards() {
        return productsOnGrid.stream()
                .map(p -> new ProductMiniatureComponent(driver, p))
                .toList();
    }

    public List<Product> getProducts() {
        return getProductCards().stream()
                .map(ProductMiniatureComponent::toProductModel)
                .toList();
    }

    public void openProduct(String productName) {
        getProductCards().stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName))
                .open();
    }

    public Product findProduct(String productName) {
        return getProducts().stream()
                .filter(p -> p.getName().equals(productName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));
    }

}