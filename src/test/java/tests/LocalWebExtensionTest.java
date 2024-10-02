package tests;

import base.BaseTest;
import pages.LocalWebExtensionPage;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LocalWebExtensionTest extends BaseTest {

    @Test(groups = {"chrome-only"})
    void webExtension() {
        LocalWebExtensionPage localWebExtensionPage = new LocalWebExtensionPage(driver);
        goToURL(initPage);

        // locate the background color property
        String mainBgColor = localWebExtensionPage.getBackgroundPageColor();

        // verify background color is now black (with the web extension having been installed)
        assertEquals(mainBgColor, "rgba(0, 0, 0, 0)");

    }
}
