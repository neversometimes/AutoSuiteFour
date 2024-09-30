package tests;

import base.BaseTest;
import pages.InsecureCertsPage;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.openqa.selenium.support.Color;


public class InsecureCertsTest extends BaseTest {
    String badSSLpage = "https://self-signed.badssl.com";

    @Test
    public void loadInsecurePage() {
        Color red = new Color(255, 0, 0, 1);
        String pgTxt = "self-signed.\nbadssl.com";
        InsecureCertsPage insecureCertsPage = new InsecureCertsPage(driver);

        goToURL(badSSLpage);

        // verify page bg color and text
        assertEquals(Color.fromString(insecureCertsPage.getPageBackgroundColor()), red);
        assertEquals(insecureCertsPage.getPageTxt(), pgTxt);
    }
}
