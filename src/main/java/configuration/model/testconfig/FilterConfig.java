package configuration.model.testconfig;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FilterConfig {
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
