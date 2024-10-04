package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LocalizationPage {

    WebDriver driver;

    public LocalizationPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "body")
    WebElement pageBody;

    //  ***********   PAGE METHODS   **************
    public String getBodyTxt() {
        return pageBody.getText();
    }

}
