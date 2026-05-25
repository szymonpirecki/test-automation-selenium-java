package pages.user.order;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderHistoryPage extends BasePage {
    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "tbody tr")
    private List<WebElement> userOrders;


    public OrderLineComponent findOrderByReference(String referenceNumber) {
        return getOrderLines().stream()
                .filter(l -> l.getReferenceNumber().equalsIgnoreCase(referenceNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Order not found for reference: " + referenceNumber));
    }

    private List<OrderLineComponent> getOrderLines() {
        List<OrderLineComponent> rows = new ArrayList<>();
        for (WebElement row : userOrders) {
            rows.add(new OrderLineComponent(driver, row));
        }
        return rows;
    }
}
