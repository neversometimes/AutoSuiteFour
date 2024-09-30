package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class GetUserMediaPage {

    WebDriver driver;
    Wait<WebDriver> wait;

    public GetUserMediaPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = "#start")
    WebElement startBtn;
    @FindBy(css = "#video-device")
    WebElement weVideoDevice;

    By videoDevice = By.cssSelector("#video-device");

    //  ***********   PAGE METHODS   **************
    public void clickStartBtn() {
        startBtn.click();
    }
    public void enableVideo() {
        Pattern nonEmptyString = Pattern.compile(".+");
        wait.until(ExpectedConditions.textMatches(videoDevice, nonEmptyString));
    }
    public String getVideoTxt() {
        return weVideoDevice.getText();
    }
}
