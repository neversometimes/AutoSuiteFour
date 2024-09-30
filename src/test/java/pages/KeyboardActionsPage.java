package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class KeyboardActionsPage {
    WebDriver driver;

    public KeyboardActionsPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(name = "my-text")
    WebElement txtBx;
    @FindBy(css = ".form-range")
    WebElement slider;

    //  ***********   PAGE METHODS   **************
    public void sendKeysToTxtBx(String str) {
        txtBx.sendKeys(str);
    }
    public String getTextInTxtBx() {
        return txtBx.getAttribute("value");
    }
    public void clearTxtBx() {
        txtBx.clear();
    }
    public String getSliderValue() {
        return slider.getAttribute("value");
    }
    public void sendKeysToSlider(Keys keys) {
        slider.sendKeys(keys);
    }


}
