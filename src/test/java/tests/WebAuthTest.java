package tests;

import base.BaseTest;
import pages.WebAuthPage;

import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class WebAuthTest extends BaseTest {
    String webAuthenticationPage = "https://webauthn.io";

    @Test (groups = {"chrome-only"})
    public void basicWebAuthn() {
        // go to site protected with Web Auth API
        goToURL(webAuthenticationPage);

        WebAuthPage webAuthPage = new WebAuthPage(driver);

        // create and register a new virtual authenticator
        VirtualAuthenticator authenticator = webAuthPage.createVirtualAuthenticator();

        // send random UUID string as key to web form
        webAuthPage.addRandomKeyStringToForm();

        // wait then try to submit using random string with virtual authenticator credentials
        webAuthPage.submitFormUsingVirtualAuthCreds();

        // try to log-in once registered
        webAuthPage.logInAfterAuth();

        // verify logged in successfully via result text
        assertTrue(webAuthPage.getSuccessTxt().contains("You're logged in!")); // assert log in success

        // test clean up - remove created virtual authenticator
        webAuthPage.removeVirtualAuthenticator(authenticator);
    }

}
