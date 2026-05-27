package searchTests;

import model.basket.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Search")
public class SearchTest extends SearchBase {

    @Test
    @DisplayName("Should return matching results when searching by a randomly selected product name")
    public void shouldReturnResultsForRandomProductTest() {
        String randomProductName = productSteps.getRandomProduct().getName();
        List<Product> results = searchSteps
                .search(randomProductName)
                .getProducts();

        assertThat(results)
                .isNotEmpty()
                .allSatisfy(product ->
                        assertThat(product.getName()).containsIgnoringCase(randomProductName));
    }

    @Test
    @DisplayName("Should show autocomplete suggestions containing the typed keyword")
    public void shouldShowAutocompleteSuggestionsTest() {
        List<String> suggestions = searchSteps
                .typeSearchQuery(testData.keyword())
                .getSearchSuggestions();

        assertThat(suggestions)
                .isNotEmpty()
                .allMatch(suggestion -> suggestion.contains(testData.keyword()));
    }
}
