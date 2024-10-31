package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MultiElementPage {

    WebDriver driver;

    public MultiElementPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = "input[id='checkBoxOption3']")
    WebElement option3chkbox;
    @FindBy(css = "label[for='honda']")
    WebElement option3Label;
    @FindBy(id = "name")
    WebElement alertTxtInput;
    @FindBy(id = "alertbtn")
    WebElement alertBtn;

    @FindBy(id = "dropdown-class-example")
    WebElement selectDD;
    Select dropDown;

    //  ***********   PAGE METHODS   **************
    public void clickOption3Chkbox() {
        option3chkbox.click();
    }
    public String getOption3ChkboxLabel() {
        //System.out.println(option3Label.getText());
        return option3Label.getText();
    }
    public void selectDropDownOfChckboxLabel() {
        dropDown = new Select(selectDD);
        dropDown.selectByVisibleText(getOption3ChkboxLabel());
    }
    public String getFirstSelectedOption() {
        dropDown = new Select(selectDD);
        return dropDown.getFirstSelectedOption().getText();
    }
    public void enterNameInAlertTextInput(String name) {
        alertTxtInput.sendKeys(name);
    }
    public void clickAlertBtn() {
        alertBtn.click();
    }
    public String getAlertTxt() {
        return driver.switchTo().alert().getText();
    }
}
