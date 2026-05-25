package pages.user.order;

import model.order.OrderDetails;
import model.order.OrderDetailsQueryable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailsPage extends BasePage implements OrderDetailsQueryable {

    @FindBy(css = "#order-infos .box:nth-of-type(1) strong")
    private WebElement orderConfirmationTitle;

    @FindBy(css = "#order-history tbody tr")
    private List<WebElement> orderStatusLines;

    @FindBy(css = "#delivery-address address")
    private WebElement deliveryAddressContainer;

    @FindBy(css = "#invoice-address address")
    private WebElement invoiceAddressContainer;

    @FindBy(css = "#order-products tfoot tr:last-child td:last-child")
    private WebElement totalPrice;


    public List<OrderStatusLineComponent> getOrderStatusLines() {
        return orderStatusLines.stream()
                .map(OrderStatusLineComponent::new)
                .collect(Collectors.toList());
    }

    public String getOrderConfirmationTitle() {
        return orderConfirmationTitle.getText();
    }

    public String getDeliveryAddress() {
        return deliveryAddressContainer.getText();
    }

    public String getInvoiceAddress() {
        return invoiceAddressContainer.getText();
    }

    public BigDecimal getTotalPrice() {
        return getBigDecimal(totalPrice);
    }


    @Override
    public OrderDetails toOrderDetailsModel() {
        var orderStatusLines = getOrderStatusLines();
        var paymentStatus = orderStatusLines.get(orderStatusLines.size() - 1).getPaymentStatus();
        return OrderDetails.builder()
                .orderConfirmationTitle(getOrderConfirmationTitle())
                .paymentStatus(paymentStatus)
                .deliveryAddress(getDeliveryAddress())
                .invoiceAddress(getInvoiceAddress())
                .totalPrice(getTotalPrice())
                .build();
    }
}
