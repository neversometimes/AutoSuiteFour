package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class WebTablePage {

    WebDriver driver;

    public WebTablePage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = ".table-display tbody tr")
    List<WebElement> webTableRows;
    @FindBy(css = ".table-display tbody th")
    List<WebElement> webTableColumns;


    //  ***********   PAGE METHODS   **************
    public int getWebTableRowCount() {
        return webTableRows.size();
    }
    public int getWebTableColumnCount() {
        return webTableColumns.size();
    }
    public List<WebElement> getWebTable2ndRow() {
        return webTableRows.get(2).findElements(By.cssSelector("td"));
    }
    public List<String> getTargetRowValues() {
        List<String> ls = new ArrayList<>();
        ls.add("Rahul Shetty");
        ls.add("Learn SQL in Practical + Database Testing from Scratch");
        ls.add("25");
        return ls;
    }
}
