package categoryTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Category")
public class CategoryTest extends CategoryBase {

    @Test
    @DisplayName("Should display correct title, product count, and filter panel for each top-level category")
    public void shouldValidateCategoryDetailsTest() {
        List<String> categoryNames = categorySteps.getCategoryNames();
        assertThat(categoryNames).withFailMessage("Couldn't get category names.").isNotEmpty();

        for (String category : categoryNames) {
            categorySteps.verifyCategoryDetails(category);
        }
    }

    @Test
    @DisplayName("Should display correct details for each subcategory within all top-level categories")
    public void shouldValidateSubCategoryDetailsTest() {
        List<String> categoryNames = categorySteps.getCategoryNames();
        assertThat(categoryNames).withFailMessage("Couldn't get category names.").isNotEmpty();

        for (String category : categoryNames) {
            categorySteps.verifySubCategoryDetails(category);
        }
    }
}
