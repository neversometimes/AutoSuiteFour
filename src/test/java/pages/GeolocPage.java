package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GeolocPage {

    WebDriver driver;
    Wait<WebDriver> wait;

    public GeolocPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(xpath = "//*[@id='get-coordinates']")
    WebElement getCoordBtn;
    @FindBy(css = "p[id$='coordinates']")
    WebElement coordList;


    //  ***********   PAGE METHODS   **************
    public void clickGetCoordBtn() {
        getCoordBtn.click();
        wait.until(ExpectedConditions.visibilityOf(coordList));
    }
    public String getLatitude() {
        String[] arr = coordList.getAttribute("innerText").split("\n");
        return arr[0];
    }
    public String getLongitude() {
        String[] arr = coordList.getAttribute("innerText").split("\n");
        return arr[1];
    }
    public DevTools getDevTools() {
        return ((ChromeDriver) driver).getDevTools();
    }
}
