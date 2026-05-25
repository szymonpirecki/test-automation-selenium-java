package pages.user.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class AccountPage extends BasePage {

    @FindBy(css = "#addresses-link")
    WebElement userAddressesLink;
    @FindBy(css = "#history-link")
    WebElement orderHistoryLink;

    public void navigateToAddresses() {
        click(userAddressesLink);
    }

    public void navigateToOrderHistory() {
        click(orderHistoryLink);
    }
}
