package tests;

import base.BaseTest;
import pages.BasicAuthPage;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class BasicAuthTest extends BaseTest {

    @Test
    public void testBasicAuth() {
        BasicAuthPage basicAuthPage = new BasicAuthPage(driver);

        // *******  Send creds using:  *******
        // protocol://username:password@domain  **  Nice WORKAROUND for remote or local runs
        String authURL = "https://guest:guest@jigsaw.w3.org/HTTP/Basic/";
        goToURL(authURL);

        assertTrue(basicAuthPage.verifyBodyText("Your browser made it!"));
    }
}
