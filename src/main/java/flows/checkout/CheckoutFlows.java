package flows.checkout;

import flows.base.BaseFlows;
import model.testdata.Address;
import org.openqa.selenium.WebDriver;
import pages.checkout.CheckoutAddressPage;
import pages.checkout.CheckoutPaymentPage;
import pages.checkout.CheckoutShippingPage;
import pages.checkout.OrderConfirmationPage;

import java.math.BigDecimal;

public class CheckoutFlows extends BaseFlows {
    public CheckoutFlows(WebDriver driver) {
        super(driver);
    }

    public CheckoutFlows enterInvoiceAddress(Address invoiceAddress) {
        at(CheckoutAddressPage.class, page -> {
            page.selectDifferentInvoiceAddress();
            page.fillAddress(invoiceAddress.street(), invoiceAddress.postCode(), invoiceAddress.city(), invoiceAddress.country());
            page.confirmAddress();
        });
        return this;
    }

    public CheckoutFlows selectDeliveryOption(BigDecimal shippingPrice) {
        at(CheckoutShippingPage.class, page -> {
            page.selectDeliveryOptionByPrice(shippingPrice);
            page.confirmShippingMethod();
        });
        return this;
    }

    public CheckoutFlows selectPayByCheck() {
        at(CheckoutPaymentPage.class).selectPayByCheck();
        return this;
    }

    public CheckoutFlows placeOrder() {
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
