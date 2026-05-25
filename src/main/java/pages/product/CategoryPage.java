package pages.product;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.util.List;
import java.util.NoSuchElementException;

public class CategoryPage extends BasePage {
    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".block-category ")
    private WebElement categoryCardBlock;

    @FindBy(css = ".total-products")
    private WebElement totalProductsLbl;

    @FindBy(css = ".category-top-menu")
    private WebElement categoryTopMenu;

    @FindBy(css = ".category-sub-menu a")
    private List<WebElement> subCategories;

    @FindBy(css = ".block-category > h1")
    private WebElement categoryHeader;

    public boolean hasSubCategories() {
        return !subCategories.isEmpty();
    }

    public void navigateToSubCategory(String subCategoryName) {
        waitForAllElements(subCategories);
        subCategories.stream()
                .filter(c -> c.getText().equalsIgnoreCase(subCategoryName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Subcategory not found: " + subCategoryName))
                .click();
    }

    public List<String> getSubCategoryNames() {
        waitForAllElements(subCategories);
        return subCategories.stream()
                .map(WebElement::getText)
                .toList();
    }

    public int getTotalProductCount() {
        return getInt(totalProductsLbl);
    }

    public String getCategoryTitle() {
        return categoryHeader.getText();
    }

}
