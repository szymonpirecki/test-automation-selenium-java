package steps.checkout;

import steps.base.BaseSteps;
import model.testdata.Address;
import pages.checkout.CheckoutAddressPage;
import pages.checkout.CheckoutPaymentPage;
import pages.checkout.CheckoutShippingPage;
import pages.checkout.OrderConfirmationPage;

import java.math.BigDecimal;

public class CheckoutSteps extends BaseSteps {

    public CheckoutSteps enterInvoiceAddress(Address invoiceAddress) {
        at(CheckoutAddressPage.class, page -> {
            page.selectDifferentInvoiceAddress();
            page.fillAddress(invoiceAddress.street(), invoiceAddress.postCode(), invoiceAddress.city(), invoiceAddress.country());
            page.confirmAddress();
        });
        return this;
    }

    public CheckoutSteps selectDeliveryOption(BigDecimal shippingPrice) {
        at(CheckoutShippingPage.class, page -> {
            page.selectDeliveryOptionByPrice(shippingPrice);
            page.confirmShippingMethod();
        });
        return this;
    }

    public CheckoutSteps selectPayByCheck() {
        at(CheckoutPaymentPage.class).selectPayByCheck();
        return this;
    }

    public CheckoutSteps placeOrder() {
        at(CheckoutPaymentPage.class, page -> {
            page.acceptTermsAndConditions();
            page.placeOrder();
        });
        return this;
    }

    public String getOrderReferenceNumber() {
        return at(OrderConfirmationPage.class).getOrderReferenceNumber();
    }
}
