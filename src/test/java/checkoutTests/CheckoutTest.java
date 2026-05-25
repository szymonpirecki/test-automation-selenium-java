package checkoutTests;

import model.basket.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Checkout")
public class CheckoutTest extends CheckoutBase {

    @Test
    @DisplayName("Should complete full purchase flow: login, add product, checkout with invoice address, and verify order in history")
    public void shouldGoThroughBuyingProcessTest() {
        loginFlows
                .navigateToLoginPage()
                .loginAs(testData.credentials().email(), testData.credentials().password())
                .cleanUpAddresses();

        Product product = productFlows
                .navigateToCategory(testData.productCategory())
                .findProduct(testData.productName());

        basketFlows
                .addProductToBasket(product, testData.productQuantity())
                .proceedToCheckout();

        String orderNumber = checkoutFlows
                .enterInvoiceAddress(testData.invoiceAddress())
                .selectDeliveryOption(testData.shippingPrice())
                .selectPayByCheck()
                .placeOrder()
                .getOrderReferenceNumber();

        accountFlows
                .navigateToAccount()
                .navigateToOrderHistory()
                .navigateToOrderDetails(orderNumber)
                .verifyOrderDetails(
                        orderNumber,
                        testData.deliveryAddressParts(),
                        testData.invoiceAddressParts(),
                        testData.expectedPaymentStatus()
                );
    }
}
