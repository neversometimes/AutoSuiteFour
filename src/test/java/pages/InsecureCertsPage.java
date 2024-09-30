package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InsecureCertsPage {

    WebDriver driver;

    public InsecureCertsPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "body")
    WebElement pageBody;
    @FindBy(css = "div[id='content'] h1")
    WebElement pageTxt;

    //  ***********   PAGE METHODS   **************
    public String getPageBackgroundColor() {
        return pageBody.getCssValue("background-color");
    }
    public String getPageTxt() {
        return pageTxt.getText();
    }

}
