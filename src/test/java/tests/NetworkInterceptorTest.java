package tests;

import base.BaseTest;
import pages.NetworkInterceptorPage;

import org.openqa.selenium.devtools.NetworkInterceptor;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.nio.file.Path;


public class NetworkInterceptorTest extends BaseTest {

    @Test (groups = {"notFF"})        // test uses CDP wrapper class
    void networkInterceptor() throws Exception {
        NetworkInterceptorPage networkInterceptorPage = new NetworkInterceptorPage(driver);
        String testPNG = "tools.png"; //found under src/test/resources) : 80x80 PNG file

        // load local img test resource
        Path img = networkInterceptorPage.loadLocalTestResource(testPNG);

        //read in resource file into byte array
        byte[] byteArr = networkInterceptorPage.readFileAsBytes(img);

        try (NetworkInterceptor interceptor = networkInterceptorPage.createNWInterceptor(byteArr)) {
            // open page with img to be replaced (found in upper right corner)
            goToURL(initPage);

            // verify PNG img width x height is now 128x128
            assertEquals(networkInterceptorPage.getWidthOfImg(), 128);
            assertEquals(networkInterceptorPage.getHeightOfImg(), 128);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}