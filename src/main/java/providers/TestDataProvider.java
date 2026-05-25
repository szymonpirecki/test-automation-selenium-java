package providers;

import configuration.handler.YamlReader;
import configuration.model.EnvironmentModel;
import configuration.model.testconfig.AddressConfig;
import model.testdata.Address;
import model.testdata.BasketTestData;
import model.testdata.CheckoutTestData;
import model.testdata.Credentials;
import model.testdata.FilterTestData;
import model.testdata.SearchTestData;

import java.math.BigDecimal;

public class TestDataProvider {

    public static CheckoutTestData checkoutTestData() {
        var c = currentEnvironment().getCheckoutConfig();
        return new CheckoutTestData(
                new Credentials(
                        resolveEnv("TEST_USER_EMAIL", c.getUser().getEmail()),
                        resolveEnv("TEST_USER_PASSWORD", c.getUser().getPassword())
                ),
                c.getUser().getFirstName(),
                c.getUser().getLastName(),
                toAddress(c.getDeliveryAddress()),
                toAddress(c.getInvoiceAddress()),
                c.getProduct().getCategory(),
                c.getProduct().getName(),
                c.getProduct().getQuantity(),
                shippingPrice(),
                c.getPaymentStatus()
        );
    }

    public static BasketTestData basketTestData() {
        var c = currentEnvironment().getBasketConfig();
        return new BasketTestData(c.getCategory(), c.getProductName(), c.getProductQuantity(), shippingPrice());
    }

    public static FilterTestData filterTestData() {
        var c = currentEnvironment().getFilterConfig();
        return new FilterTestData(c.getCategory(), c.getMinPrice(), c.getMaxPrice());
    }

    public static SearchTestData searchTestData() {
        return new SearchTestData(currentEnvironment().getSearchConfig().getKeyword());
    }

    private static EnvironmentModel currentEnvironment() {
        return YamlReader.getInstance().getYamlModel().getCurrentEnvironment();
    }

    private static String resolveEnv(String envVar, String fallback) {
        var value = System.getenv(envVar);
        return (value != null && !value.isBlank()) ? value : fallback;
    }

    private static Address toAddress(AddressConfig config) {
        return new Address(config.getAddress(), config.getPostCode(), config.getCity(), config.getCountry());
    }

    private static BigDecimal shippingPrice() {
        return new BigDecimal(System.getProperty("shippingPrice"));
    }
}
