package categoryTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Category")
public class CategoryTest extends CategoryBase {

    @Test
    @DisplayName("Should display correct title, product count, and filter panel for each top-level category")
    public void shouldValidateCategoryDetailsTest() {
        var categoryNames = categoryFlows.getCategoryNames();
        assertThat(categoryNames).withFailMessage("Couldn't get category names.").isNotEmpty();

        for (String category : categoryNames) {
            categoryFlows.verifyCategoryDetails(category);
        }
    }

    @Test
    @DisplayName("Should display correct details for each subcategory within all top-level categories")
    public void shouldValidateSubCategoryDetailsTest() {
        var categoryNames = categoryFlows.getCategoryNames();
        assertThat(categoryNames).withFailMessage("Couldn't get category names.").isNotEmpty();

        for (String category : categoryNames) {
            categoryFlows.verifySubCategoryDetails(category);
        }
    }
}
