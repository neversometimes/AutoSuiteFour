package tests;

import base.BaseTest;
import pages.RemoteFilePage;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.IOException;

public class RemoteFileTest extends BaseTest {
    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    // **** LOCAL TEST ONLY ****
    @Test
    void upLoadTempFile() throws IOException {
        RemoteFilePage remoteFilePage = new RemoteFilePage(driver);

        goToURL(webFormPage);
        String prefix = "xfiles"; String suffix = ".tmp";

        // create temp file (prefix, suffix)
        remoteFilePage.createTempFile(prefix, suffix);

        // click submit on form control
        remoteFilePage.submitSubmitBtn();

        // verify submit form url, header and paragraph page text
        assertNotSame(getPageURL(), webFormPage);   // current URL != webFormPage

        assertEquals(remoteFilePage.getHeaderTxt(), "Form submitted"); // verify header text
        assertEquals(remoteFilePage.getParagraphTxt(), "Received!");  // verify paragraph text

    }

}
