package pages.basket;

import lombok.Getter;
import model.basket.BasketPopUp;
import model.basket.BasketPopUpQueryable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.math.BigDecimal;

@Getter
public class BasketPopUpPage extends BasePage implements BasketPopUpQueryable {


    public BasketPopUpPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#myModalLabel")
    private WebElement popupTitle;

    @FindBy(css = ".product-name")
    private WebElement productName;

    @FindBy(css = ".modal-body .product-price")
    private WebElement productPrice;

    @FindBy(css = ".modal-body .product-quantity")
    private WebElement productQuantity;

    @FindBy(css = ".modal-body .cart-products-count")
    private WebElement productCount;

    @FindBy(css = ".subtotal")
    private WebElement subTotalValue;

    @FindBy(css = ".shipping ")
    private WebElement shippingValue;

    @FindBy(css = ".product-total .value")
    private WebElement productTotalValue;

    @FindBy(css = ".cart-content-btn .btn-secondary")
    private WebElement continueShoppingBtn;

    @FindBy(css = ".cart-content-btn .btn-primary")
    private WebElement proceedToCheckoutBtn;


    public String getProductName() {
        return productName.getText();
    }

    public BigDecimal getProductPrice() {
        return getBigDecimal(productPrice);
    }

    public int getProductQuantity() {
        return getInt(productQuantity);
    }

    public int getProductCount() {
        return getInt(productCount);
    }

    public BigDecimal getProductTotalValue() {
        return getBigDecimal(productTotalValue);
    }

    public BigDecimal getShippingValue() {
        return getBigDecimal(shippingValue);
    }

    public BigDecimal getSubTotalValue() {
        return getBigDecimal(subTotalValue);
    }

    public void continueShopping() {
        click(continueShoppingBtn);
    }

    public void proceedToCheckout() {
        click(proceedToCheckoutBtn);
    }

    @Override
    public BasketPopUp toBasketPopUpModel() {
        waitForElement(popupTitle);
        return BasketPopUp.builder()
                .productName(getProductName())
                .productPrice(getProductPrice())
                .productQuantity(getProductQuantity())
                .productCount(getProductCount())
                .shippingValue(getShippingValue())
                .subTotalValue(getSubTotalValue())
                .productTotalValue(getProductTotalValue())
                .build();
    }
}
