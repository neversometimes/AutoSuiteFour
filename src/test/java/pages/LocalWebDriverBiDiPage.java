package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.events.CdpEventTypes;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class LocalWebDriverBiDiPage {

    WebDriver driver;
    HasLogEvents logger;
    JavascriptExecutor js;

    public LocalWebDriverBiDiPage (WebDriver driver) {
        this.driver = driver;
        this.logger = (HasLogEvents) driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "img")
    WebElement img;

    //  ***********   PAGE METHODS   **************
    public void setDomMutationEventListener(AtomicReference<DomMutationEvent> seen, CountDownLatch latch) {
        // Listener for DOM mutation events. Expects to capture
        // only one event, synchronized using a countdown latch.
        logger.onLogEvent(CdpEventTypes.domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));
    }
    public void swapOutImgJS(String src) {
        String script = String.format("arguments[0].src = '%s';", src);
        js.executeScript(script, img);  // DOM mutation by executing JS to change image source.
    }
    public void setConsoleEventListener(CountDownLatch latch) {
        //  Listener for console events.  Expects to capture # of latch
        //   events synchronized using a countdown latch.
        logger.onLogEvent((CdpEventTypes.consoleEvent(consoleEvent -> {
            System.out.println(consoleEvent.getTimestamp()
                    + " " + consoleEvent.getType()
                    + ": " + consoleEvent.getMessages() ); // each println will trigger a console event
            latch.countDown();
        })));
    }


}
