package pages.user.order;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class OrderStatusLineComponent extends BasePage {
    public OrderStatusLineComponent(WebElement parent) {
        super(parent);
    }

    @FindBy(xpath = "./td[1]")
    private WebElement date;

    @FindBy(xpath = "./td[2]")
    private WebElement paymentStatus;

    public String getDate() {
        return date.getText().trim();
    }

    public String getPaymentStatus() {
        return paymentStatus.getText().trim();
    }
}