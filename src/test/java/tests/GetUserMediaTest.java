package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import pages.GetUserMediaPage;

public class GetUserMediaTest extends BaseTest {
    String userMediaPage = "https://bonigarcia.dev/selenium-webdriver-java/get-user-media.html";

    @Test (groups = {"chrome-only"})
    public void useFakeMedia() throws InterruptedException{
        GetUserMediaPage getUserMediaPage = new GetUserMediaPage(driver);

        goToURL(userMediaPage);

        getUserMediaPage.clickStartBtn();
        getUserMediaPage.enableVideo();

        assertNotEquals(getUserMediaPage.getVideoTxt(), null);
        Thread.sleep(1000);  // 1s pause just for the video beep
    }
}
