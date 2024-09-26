package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasicAuthPage {

    WebDriver driver;

    public BasicAuthPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "body")
    WebElement body;

    //  ***********   PAGE METHODS   **************
    public boolean verifyBodyText(String str) {
            return body.getText().contains(str);
    }
}
