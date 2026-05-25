package pages.user.address;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.util.List;

public class AddressPage extends BasePage {

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".address")
    private List<WebElement> userAddressesContainers;

    public void deleteAddressesWithoutFlag(String flag) {
        getAddressContainers().stream()
                .filter(ac -> !ac.getAddressTitle().contains(flag))
                .forEach(AddressContainerComponent::deleteAddress);
    }

    private List<AddressContainerComponent> getAddressContainers() {
        waitForAllElements(userAddressesContainers);
        return userAddressesContainers.stream()
                .map(ac -> new AddressContainerComponent(driver, ac))
                .toList();
    }
}
