package configuration.assertJConfig;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.math.BigDecimal;

public class AssertJConfigHelper {

    public static RecursiveComparisonConfiguration getBigDecimalComparisonConfig() {
        return RecursiveComparisonConfiguration.builder()
                .withComparatorForType(
                        (bd1, bd2) -> bd1.stripTrailingZeros().compareTo(bd2.stripTrailingZeros()),
                        BigDecimal.class)
                .build();
    }
}
