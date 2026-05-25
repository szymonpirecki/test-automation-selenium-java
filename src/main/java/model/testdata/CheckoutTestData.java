package model.testdata;

import java.math.BigDecimal;
import java.util.List;

public record CheckoutTestData(
        Credentials credentials,
        String firstName,
        String lastName,
        Address deliveryAddress,
        Address invoiceAddress,
        String productCategory,
        String productName,
        int productQuantity,
        BigDecimal shippingPrice,
        String expectedPaymentStatus
) {
    public List<String> deliveryAddressParts() {
        return deliveryAddress.toAddressParts(firstName, lastName);
    }

    public List<String> invoiceAddressParts() {
        return invoiceAddress.toAddressParts(firstName, lastName);
    }
}
