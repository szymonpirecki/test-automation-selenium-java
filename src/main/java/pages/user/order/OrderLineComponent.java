package pages.user.order;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class OrderLineComponent extends BasePage {

    public OrderLineComponent(WebElement parent) {
        super(parent);
    }

    @FindBy(css = "th")
    private WebElement referenceNumber;

    @FindBy(xpath = "./td[1]")
    private WebElement date;

    @FindBy(xpath = "./td[2]")
    private WebElement totalPrice;

    @FindBy(xpath = "./td[4]")
    private WebElement paymentStatus;

    @FindBy(xpath = "./td[6]/a[@data-link-action='view-order-details']")
    private WebElement orderDetailsLink;

    public void viewDetails() {
        click(orderDetailsLink);
    }

    public String getReferenceNumber() {
        return referenceNumber.getText();
    }
}
