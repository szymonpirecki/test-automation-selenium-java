package model.testdata;

import java.math.BigDecimal;

public record FilterTestData(
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
