package providers;

import configuration.handler.YamlReader;
import configuration.model.EnvironmentModel;
import configuration.model.testconfig.AddressConfig;
import configuration.model.testconfig.BasketConfig;
import configuration.model.testconfig.CheckoutConfig;
import configuration.model.testconfig.FilterConfig;
import model.testdata.Address;
import model.testdata.BasketTestData;
import model.testdata.CheckoutTestData;
import model.testdata.Credentials;
import model.testdata.FilterTestData;
import model.testdata.SearchTestData;

import java.math.BigDecimal;

public class TestDataProvider {

    public static CheckoutTestData checkoutTestData() {
        CheckoutConfig config = currentEnvironment().getCheckoutConfig();
        return new CheckoutTestData(
                new Credentials(
                        resolveEnv("TEST_USER_EMAIL", config.getUser().getEmail()),
                        resolveEnv("TEST_USER_PASSWORD", config.getUser().getPassword())
                ),
                config.getUser().getFirstName(),
                config.getUser().getLastName(),
                toAddress(config.getDeliveryAddress()),
                toAddress(config.getInvoiceAddress()),
                config.getProduct().getCategory(),
                config.getProduct().getName(),
                config.getProduct().getQuantity(),
                shippingPrice(),
                config.getPaymentStatus()
        );
    }

    public static BasketTestData basketTestData() {
        BasketConfig config = currentEnvironment().getBasketConfig();
        return new BasketTestData(config.getCategory(), config.getProductName(), config.getProductQuantity(), shippingPrice());
    }

    public static FilterTestData filterTestData() {
        FilterConfig config = currentEnvironment().getFilterConfig();
        return new FilterTestData(config.getCategory(), config.getMinPrice(), config.getMaxPrice());
    }

    public static SearchTestData searchTestData() {
        return new SearchTestData(currentEnvironment().getSearchConfig().getKeyword());
    }

    private static EnvironmentModel currentEnvironment() {
        return YamlReader.getInstance().getYamlModel().getCurrentEnvironment();
    }

    private static String resolveEnv(String envVar, String fallback) {
        String value = System.getenv(envVar);
        return (value != null && !value.isBlank()) ? value : fallback;
    }

    private static Address toAddress(AddressConfig config) {
        return new Address(config.getAddress(), config.getPostCode(), config.getCity(), config.getCountry());
    }

    private static BigDecimal shippingPrice() {
        return new BigDecimal(System.getProperty("shippingPrice"));
    }
}
