package basketTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Basket")
public class BasketTest extends BasketBase {

    @Test
    @DisplayName("Should add a product to the basket and reflect the correct popup content and cart count")
    public void shouldAddProductToBasketTest() {
        var product = productFlows
                .navigateToCategory(testData.category())
                .findProduct(testData.productName());

        basketFlows
                .addProductToBasket(product, testData.productQuantity())
                .verifyCartPopUpContent(product, testData.productQuantity(), testData.shippingPrice())
                .closeCartPopup()
                .verifyCartItemCount(testData.productQuantity());
    }

    @Test
    @DisplayName("Should add 10 random products and verify basket contents match expected state")
    public void shouldAddRandomProductsToBasketTest() {
        for (int i = 0; i < 10; i++) {
            basketFlows
                    .addProductToBasket(productFlows.getRandomProduct(), productFlows.getRandomQuantity())
                    .continueShopping();
        }
        basketFlows.verifyCartContents();
    }

    @Test
    @DisplayName("Should remove all products from the basket one by one and verify total updates correctly")
    public void shouldRemoveProductsFromBasketTest() {
        for (int i = 0; i < 2; i++) {
            basketFlows
                    .addProductToBasket(productFlows.getRandomProduct(), productFlows.getRandomQuantity())
                    .continueShopping();
        }
        var itemCount = basketFlows
                .navigateToBasket()
                .getActualBasket().getBasketContent().size();

        assertThat(itemCount)
                .withFailMessage("Basket should not be empty before removal")
                .isGreaterThan(0);

        for (int i = 0; i < itemCount; i++) {
            basketFlows
                    .verifyCartTotalValue()
                    .removeFirstItem()
                    .verifyCartTotalValue();
        }
        basketFlows.verifyCartItemCount(0);
    }
}
