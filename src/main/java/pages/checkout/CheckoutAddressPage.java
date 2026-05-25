package pages.checkout;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

@Getter
public class CheckoutAddressPage extends BasePage {

    @FindBy(css = "a[data-link-action='different-invoice-address']")
    WebElement differentInvoiceAddressLink;
    @FindBy(css = "input[name='address1']")
    WebElement addressInput;
    @FindBy(css = "input[name='postcode']")
    WebElement postCodeInput;
    @FindBy(css = "input[name='city']")
    WebElement cityInput;
    @FindBy(css = "select[name='id_country']")
    WebElement countrySelect;
    @FindBy(css = "button[name='confirm-addresses']")
    WebElement confirmAddressButton;

    public void selectDifferentInvoiceAddress() {
        click(differentInvoiceAddressLink);
    }

    public void fillAddress(String address, String postCode, String city, String country) {
        sendKeys(addressInput, address);
        sendKeys(postCodeInput, postCode);
        sendKeys(cityInput, city);
        selectByVisibleText(countrySelect, country);
    }

    public void confirmAddress() {
        click(confirmAddressButton);
    }

}