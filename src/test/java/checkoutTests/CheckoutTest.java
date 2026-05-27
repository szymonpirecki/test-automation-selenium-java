package checkoutTests;

import model.basket.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Checkout")
public class CheckoutTest extends CheckoutBase {

    @Test
    @DisplayName("Should complete full purchase flow: login, add product, checkout with invoice address, and verify order in history")
    public void shouldGoThroughBuyingProcessTest() {
        loginSteps
                .navigateToLoginPage()
                .loginAs(testData.credentials().email(), testData.credentials().password())
                .cleanUpAddresses();

        Product product = productSteps
                .navigateToCategory(testData.productCategory())
                .findProduct(testData.productName());

        basketSteps
                .addProductToBasket(product, testData.productQuantity())
                .proceedToCheckout();

        String orderNumber = checkoutSteps
                .enterInvoiceAddress(testData.invoiceAddress())
                .selectDeliveryOption(testData.shippingPrice())
                .selectPayByCheck()
                .placeOrder()
                .getOrderReferenceNumber();

        accountSteps
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
