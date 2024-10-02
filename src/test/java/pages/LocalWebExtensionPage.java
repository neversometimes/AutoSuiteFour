package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LocalWebExtensionPage {

    WebDriver driver;

    public LocalWebExtensionPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "main")
    WebElement bgColor;

    //  ***********   PAGE METHODS   **************
    public String getBackgroundPageColor() {
        return bgColor.getCssValue("background-color");
    }
}
