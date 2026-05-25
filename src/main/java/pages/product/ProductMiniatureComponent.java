package pages.product;

import model.basket.Product;
import model.basket.ProductQueryable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.math.BigDecimal;

public class ProductMiniatureComponent extends BasePage implements ProductQueryable {

    public ProductMiniatureComponent(WebElement parent) {
        super(parent);
    }

    @FindBy(css = ".thumbnail")
    private WebElement productImage;

    @FindBy(css = ".product-title")
    private WebElement productTitle;

    @FindBy(css = ".price")
    private WebElement productPrice;

    public String getName() {
        waitForElement(productTitle);
        return productTitle.getText();
    }

    public BigDecimal getPrice() {
        return getBigDecimal(productPrice);
    }

    public void open() {
        waitForElement(productTitle);
        productTitle.click();
    }


    @Override
    public Product toProductModel() {
        return new Product(getName(), getPrice());
    }
}
