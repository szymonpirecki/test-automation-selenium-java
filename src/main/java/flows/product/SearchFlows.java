package flows.product;

import flows.base.BaseFlows;
import org.openqa.selenium.WebDriver;
import pages.home.HeaderPage;

import java.util.List;

public class SearchFlows extends BaseFlows {
    public SearchFlows(WebDriver driver) {
        super(driver);
    }

    public SearchFlows typeSearchQuery(String query) {
        at(HeaderPage.class).typeInSearchBox(query);
        return this;
    }

    public ProductFlows search(String query) {
        typeSearchQuery(query);
        at(HeaderPage.class).submitSearch();
        return new ProductFlows(driver);
    }

    public List<String> getSearchSuggestions() {
        return at(HeaderPage.class).getSearchSuggestions();
    }
}
