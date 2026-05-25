package pages.checkout;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class OrderConfirmationPage extends BasePage {

    @FindBy(css = "#order-details > ul li:nth-of-type(1)")
    private WebElement orderReferenceNumber;

    public String getOrderReferenceNumber() {
        String prefix = "Order reference: ";
        return orderReferenceNumber.getText().substring(prefix.length());
    }
}