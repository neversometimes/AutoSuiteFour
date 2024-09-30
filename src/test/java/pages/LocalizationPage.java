package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalizationPage {

    WebDriver driver;

    public LocalizationPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "body")
    WebElement pageBody;
    @FindBy(name = "my-file")
    WebElement myFile;
    @FindBy(css = "button[type='submit']")
    WebElement submitBtn;
    @FindBy(css = "div h1[class='display-6']")
    WebElement h1Txt;
    @FindBy(css = "div p[class='lead']")
    WebElement pTxt;

    //  ***********   PAGE METHODS   **************
    public String getBodyTxt() {
        return pageBody.getText();
    }
    public void createTempFile(String prefix, String suffix) throws IOException {
        Path tempFile = Files.createTempFile(prefix, suffix); // create unique temp path+file
        myFile.sendKeys(tempFile.toAbsolutePath().toString());  //send abs path+file to file input
        // System.out.println(tempFile.toAbsolutePath().toString());
    }
    public void submitSubmitBtn() {
        submitBtn.submit();
    }
    public String getHeaderTxt() {
        return h1Txt.getText();
    }
    public String getParagraphTxt() {
        return pTxt.getText();
    }
}
