package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class OSNotificationPage {

    WebDriver driver;
    JavascriptExecutor js;

    public OSNotificationPage (WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************


    //  ***********   PAGE METHODS   **************
    public Object executeJS (String script) {
        return js.executeAsyncScript(script);
    }

}
