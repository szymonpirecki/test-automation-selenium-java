package pages.checkout;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.math.BigDecimal;

public class DeliveryOptionComponent extends BasePage {
    public DeliveryOptionComponent(WebElement parent) {
        super(parent);
    }

    @FindBy(css = ".carrier-price")
    private WebElement price;

    @FindBy(css = "input[type='radio']")
    private WebElement radioButton;

    public BigDecimal getPrice() {
        if (price.getText().equalsIgnoreCase("free")) {
            return new BigDecimal(0);
        }
        return getBigDecimal(price);
    }

    public void selectDeliveryOption() {
        selectRadioButton(radioButton);
    }
}