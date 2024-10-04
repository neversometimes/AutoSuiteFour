package tests;

import base.BaseTest;

import org.openqa.selenium.PageLoadStrategy;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.time.Duration;


public class PageLoadTest extends BaseTest {
    //  .NORMAL : default mode.  WebDriver waits until entire page is loaded.
    //  .EAGER : WebDriver waits until HTML doc has finished loading/parsing (sub resources still loading).
    //  .NONE : WebDriver waits only until HTML doc is downloaded.

    @Test
    public void pageLoadTime() {
        String browserName = getBrowserName();
        String targetPage = "https://www.apple.com";

        if (browserName.equalsIgnoreCase("chrome")) {
            cOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        } else if (browserName.equalsIgnoreCase("edge")) {
            eOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            targetPage = "https://www.amazon.com";
        } else {
            ffOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
            targetPage = "https://www.yahoo.com";
        }

        // get page load start time
        long initMillis = System.currentTimeMillis();
        goToURL(targetPage);
        // get page load duration time
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);

        assertTrue(elapsed.toMillis() > 100 );      // setting min time to load > 100ms
        //System.out.println(browserName + ": " + elapsed.toMillis() + "ms");
    }
}