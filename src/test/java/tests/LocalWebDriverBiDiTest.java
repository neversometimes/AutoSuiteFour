package tests;

import base.BaseTest;
import pages.LocalWebDriverBiDiPage;

import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

// **** This test class is chrome-only.  The WebDriver BiDi interface currently only works   **********
//       atop CDP (CdpEventTypes), but is expected to be implemented more broadly "in the future".

public class LocalWebDriverBiDiTest extends BaseTest {
    String consoleLogsPage = "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html";

    @Test (groups={"notFF"})
    public void domMutationEvent() throws InterruptedException {
        LocalWebDriverBiDiPage localWebDriverBiDiPage = new LocalWebDriverBiDiPage(driver);
        CountDownLatch latch = new CountDownLatch(1);  // expects 1 event to be captured
        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        String newSrc = "img/award.png";

        goToURL(initPage);

        // set DOM mutation event listener
        localWebDriverBiDiPage.setDomMutationEventListener(seen, latch);

        // force DOM mutation event by executing javascript swap of page image
        localWebDriverBiDiPage.swapOutImgJS(newSrc);

        // verify event occurs within 5s
        assertTrue(latch.await(5, TimeUnit.SECONDS));

        //  simple check ensure the image source has actually been changed
        assertTrue(seen.get().getElement().getAttribute("src").endsWith(newSrc));
    }

    @Test (groups = {"notFF"})
    public void consoleEvent() throws InterruptedException {
        LocalWebDriverBiDiPage localWebDriverBiDiPage = new LocalWebDriverBiDiPage(driver);
        CountDownLatch latch = new CountDownLatch(4);

        // set console event listener then capture 4 events from println to console
        localWebDriverBiDiPage.setConsoleEventListener(latch);

        goToURL(consoleLogsPage);

        // verify event occurs within 5s
        assertTrue(latch.await(5, TimeUnit.SECONDS));

    }

}
