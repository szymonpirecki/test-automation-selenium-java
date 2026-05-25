package flows.user;

import flows.base.BaseFlows;
import model.order.OrderDetails;
import org.assertj.core.api.SoftAssertions;
import pages.home.HeaderPage;
import pages.user.account.AccountPage;
import pages.user.address.AddressPage;
import pages.user.order.OrderDetailsPage;
import pages.user.order.OrderHistoryPage;
import pages.user.order.OrderLineComponent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountFlows extends BaseFlows {

    public void cleanUpAddresses() {
        navigateToAddresses();
        at(AddressPage.class).deleteAddressesWithoutFlag("DON'T DELETE");
    }

    public AccountFlows navigateToAccount() {
        at(HeaderPage.class).navigateToAccount();
        return this;
    }

    public AccountFlows navigateToOrderHistory() {
        at(AccountPage.class).navigateToOrderHistory();
        return this;
    }

    public AccountFlows navigateToOrderDetails(String orderNumber) {
        Optional<OrderLineComponent> orderLine = Optional.ofNullable(at(OrderHistoryPage.class).findOrderByReference(orderNumber));
        assertThat(orderLine)
                .as("Order line should be present for reference: " + orderNumber)
                .isPresent();
        orderLine.ifPresent(OrderLineComponent::viewDetails);
        return this;
    }

    private void navigateToAddresses() {
        at(AccountPage.class).navigateToAddresses();
    }

    private String buildExpectedConfirmationTitle(String orderReferenceNumber) {
        String pattern = "Order Reference " + orderReferenceNumber + " - placed on " + new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        return Pattern.quote(pattern);
    }

    // Assertions
    public void verifyOrderDetails(String orderNumber, List<String> deliveryAddressContent, List<String> invoiceAddressContent, String expectedPaymentStatus) {
        SoftAssertions softly = new SoftAssertions();
        OrderDetails orderDetails = at(OrderDetailsPage.class).toOrderDetailsModel();
        verifyAddressContains(softly, orderDetails.getInvoiceAddress(), invoiceAddressContent);
        verifyAddressContains(softly, orderDetails.getDeliveryAddress(), deliveryAddressContent);
        verifyConfirmationTitle(softly, orderDetails, orderNumber);
        verifyPaymentStatus(softly, orderDetails, expectedPaymentStatus);
        softly.assertAll();
    }

    private void verifyConfirmationTitle(SoftAssertions softly, OrderDetails orderDetails, String orderNumber) {
        softly.assertThat(orderDetails.getOrderConfirmationTitle()).matches(buildExpectedConfirmationTitle(orderNumber));
    }

    private void verifyAddressContains(SoftAssertions softly, String address, List<String> expectedContent) {
        for (String element : expectedContent) {
            softly.assertThat(address).contains(element);
        }
    }

    private void verifyPaymentStatus(SoftAssertions softly, OrderDetails orderDetails, String expectedPaymentStatus) {
        softly.assertThat(orderDetails.getPaymentStatus()).isEqualTo(expectedPaymentStatus);
    }
}
