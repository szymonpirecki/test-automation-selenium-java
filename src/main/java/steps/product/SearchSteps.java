package steps.product;

import steps.base.BaseSteps;
import pages.home.HeaderPage;

import java.util.List;

public class SearchSteps extends BaseSteps {

    public SearchSteps typeSearchQuery(String query) {
        at(HeaderPage.class).typeInSearchBox(query);
        return this;
    }

    public ProductSteps search(String query) {
        typeSearchQuery(query);
        at(HeaderPage.class).submitSearch();
        return new ProductSteps();
    }

    public List<String> getSearchSuggestions() {
        return at(HeaderPage.class).getSearchSuggestions();
    }
}
