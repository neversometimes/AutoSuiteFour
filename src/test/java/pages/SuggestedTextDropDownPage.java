package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SuggestedTextDropDownPage {

    WebDriver driver;

    public SuggestedTextDropDownPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(id = "autocomplete")
    WebElement suggestTxtBox;
    @FindBy(css = "li[class='ui-menu-item']")
    List<WebElement> suggestList;

    //  ***********   PAGE METHODS   **************
    public void addTextToSuggestInput(String str) {
        suggestTxtBox.sendKeys(str);
    }
    public List<WebElement> getDropDownOptions() {
        return suggestList;
    }
    public List<WebElement> filterSuggestList(String str) {
        return getDropDownOptions().stream().filter(s->s.getText().contains(str)).toList();
    }
    public void clickFilteredItem(List<WebElement> filteredList) {
        filteredList.getFirst().click();
    }
    public String getSuggestedText() {
        return suggestTxtBox.getAttribute("value");
    }

}
