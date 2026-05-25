package configuration.model.testconfig;

import lombok.Getter;

@Getter
public class CheckoutConfig {
    private String paymentStatus;
    private UserConfig user;
    private AddressConfig deliveryAddress;
    private AddressConfig invoiceAddress;
    private ProductConfig product;
}
