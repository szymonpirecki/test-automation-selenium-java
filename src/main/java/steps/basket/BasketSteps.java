package steps.basket;

import configuration.assertJConfig.AssertJConfigHelper;
import steps.base.BaseSteps;
import lombok.Getter;
import model.basket.Basket;
import model.basket.BasketLine;
import model.basket.BasketPopUp;
import model.basket.Product;
import pages.basket.BasketPage;
import pages.basket.BasketPopUpPage;
import pages.home.HeaderPage;
import pages.product.ProductGridPage;
import pages.product.ProductPage;
import providers.UrlProvider;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class BasketSteps extends BaseSteps {

    private final Basket expectedBasket = new Basket();

    public Basket getActualBasket() {
        navigateToBasket();
        return at(BasketPage.class).toBasketModel();
    }

    public BasketSteps navigateToBasket() {
        driver.get(UrlProvider.BASKET_URL.getUrl());
        return this;
    }

    public BasketSteps addProductToBasket(Product product, int quantity) {
        at(ProductGridPage.class).openProduct(product.getName());
        at(ProductPage.class)
                .setQuantity(quantity)
                .addToCart();
        expectedBasket.addToBasket(product, quantity);
        return this;
    }

    public BasketSteps closeCartPopup() {
        at(BasketPopUpPage.class).continueShopping();
        return this;
    }

    public void continueShopping() {
        at(BasketPopUpPage.class).continueShopping();
        at(HeaderPage.class).navigateToHomePage();
    }

    public void proceedToCheckout() {
        at(BasketPopUpPage.class).proceedToCheckout();
        at(BasketPage.class).proceedToCheckout();
    }

    public BasketSteps removeFirstItem() {
        navigateToBasket();
        BasketLine firstLine = at(BasketPage.class).getFirstCartLine();
        expectedBasket.removeFromBasket(firstLine);
        at(BasketPage.class).removeProduct(firstLine.getProduct());
        return this;
    }

    // Assertions
    public BasketSteps verifyCartPopUpContent(Product product, int quantity, BigDecimal shippingPrice) {
        at(BasketPopUpPage.class, p -> {
            BasketPopUp actual = p.toBasketPopUpModel();
            BasketPopUp expected = BasketPopUp.createExpectedPopUpContent(expectedBasket, product, quantity, shippingPrice);

            assertThat(actual)
                    .usingRecursiveComparison(AssertJConfigHelper.getBigDecimalComparisonConfig())
                    .isEqualTo(expected);
        });
        return this;
    }

    public void verifyCartItemCount(int expectedCount) {
        int actual = at(HeaderPage.class).getCartItemCount();
        assertThat(actual)
                .isEqualTo(expectedCount)
                .withFailMessage("Cart item count does not match the expected value");
    }

    public void verifyCartContents() {
        assertThat(getActualBasket())
                .usingRecursiveComparison(AssertJConfigHelper.getBigDecimalComparisonConfig())
                .isEqualTo(expectedBasket);
    }

    public BasketSteps verifyCartTotalValue() {
        BigDecimal expected = getExpectedBasket().getBasketTotalValue();
        BigDecimal actual = getActualBasket().getBasketTotalValue();
        assertThat(actual).isEqualTo(expected);
        return this;
    }
}
