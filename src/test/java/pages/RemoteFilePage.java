package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RemoteFilePage {

    WebDriver driver;

    public RemoteFilePage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = "div h1[class='display-6']")
    WebElement h1Txt;
    @FindBy(css = "div p[class='lead']")
    WebElement pTxt;
    @FindBy(name = "my-file")
    WebElement myFile;
    @FindBy(css = "button[type='submit']")
    WebElement submitBtn;


    //  ***********   PAGE METHODS   **************
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
