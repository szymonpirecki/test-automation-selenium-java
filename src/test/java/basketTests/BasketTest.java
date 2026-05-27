package basketTests;

import lombok.extern.slf4j.Slf4j;
import model.basket.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Basket")
public class BasketTest extends BasketBase {

    @Test
    @DisplayName("Should add a product to the basket and reflect the correct popup content and cart count")
    public void shouldAddProductToBasketTest() {
        Product product = productSteps
                .navigateToCategory(testData.category())
                .findProduct(testData.productName());

        basketSteps
                .addProductToBasket(product, testData.productQuantity())
                .verifyCartPopUpContent(product, testData.productQuantity(), testData.shippingPrice())
                .closeCartPopup()
                .verifyCartItemCount(testData.productQuantity());
    }

    @Test
    @DisplayName("Should add 10 random products and verify basket contents match expected state")
    public void shouldAddRandomProductsToBasketTest() {
        for (int i = 0; i < 10; i++) {
            basketSteps
                    .addProductToBasket(productSteps.getRandomProduct(), productSteps.getRandomQuantity())
                    .continueShopping();
        }
        basketSteps.verifyCartContents();
    }

    @Test
    @DisplayName("Should remove all products from the basket one by one and verify total updates correctly")
    public void shouldRemoveProductsFromBasketTest() {
        for (int i = 0; i < 2; i++) {
            basketSteps
                    .addProductToBasket(productSteps.getRandomProduct(), productSteps.getRandomQuantity())
                    .continueShopping();
        }
        int itemCount = basketSteps
                .navigateToBasket()
                .getActualBasket().getBasketContent().size();

        assertThat(itemCount)
                .withFailMessage("Basket should not be empty before removal")
                .isGreaterThan(0);

        for (int i = 0; i < itemCount; i++) {
            basketSteps
                    .verifyCartTotalValue()
                    .removeFirstItem()
                    .verifyCartTotalValue();
        }
        basketSteps.verifyCartItemCount(0);
    }
}
