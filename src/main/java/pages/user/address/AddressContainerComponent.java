package pages.user.address;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;

public class AddressContainerComponent extends BasePage {
    public AddressContainerComponent(WebDriver driver, WebElement parent) {
        super(driver, parent);
    }

    @FindBy(css = ".address-body>h4")
    private WebElement addressTitle;

    @FindBy(css = ".address-footer>a[data-link-action='delete-address']")
    private WebElement deleteAddressBtn;

    public String getAddressTitle() {
        return addressTitle.getText();
    }

    public void deleteAddress() {
        click(deleteAddressBtn);
        defaultWait.until(ExpectedConditions.stalenessOf(deleteAddressBtn));
    }

}
