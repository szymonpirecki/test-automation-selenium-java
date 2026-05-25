package model.testdata;

import java.math.BigDecimal;

public record BasketTestData(
        String category,
        String productName,
        int productQuantity,
        BigDecimal shippingPrice
) {}
