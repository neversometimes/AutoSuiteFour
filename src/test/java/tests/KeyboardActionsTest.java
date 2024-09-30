package tests;

import base.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import pages.KeyboardActionsPage;

import static org.testng.Assert.*;

public class KeyboardActionsTest extends BaseTest {
    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @Test
    void sendKeys(){
        KeyboardActionsPage keyboardActionsPage = new KeyboardActionsPage(driver);

        String textValue = "Takin' It To The Street";

        goToURL(webFormPage);

        // send textValue string via sendKeys()
        keyboardActionsPage.sendKeysToTxtBx(textValue);

        // verify what I sent is what got entered
        assertEquals(keyboardActionsPage.getTextInTxtBx(), textValue);

        // clear textbox
        keyboardActionsPage.clearTxtBx();

        // verify textbox text is now empty
        assertTrue(keyboardActionsPage.getTextInTxtBx().isEmpty());
    }

    @Test
    void rangeSlider() {
        KeyboardActionsPage keyboardActionsPage = new KeyboardActionsPage(driver);
        Keys keyRtArrow = Keys.ARROW_RIGHT;
        Keys keyLfArrow = Keys.ARROW_LEFT;

        goToURL(webFormPage);

        // get init slider value
        String initSliderVal = keyboardActionsPage.getSliderValue();
        assertEquals(initSliderVal, "5");

        // use arrow key to move slider to right 5x
        for (int i = 0; i < 5; i++) {
            keyboardActionsPage.sendKeysToSlider(keyRtArrow);
        }

        // get current slider value
        String currSliderVal = keyboardActionsPage.getSliderValue();

        // verify slider value after moving
        assertEquals(currSliderVal, "10");

        // use arrow key to move slider to left 7x
        for (int i = 0; i < 7; i++) {
            keyboardActionsPage.sendKeysToSlider(keyLfArrow);
        }

        String lastSliderVal = keyboardActionsPage.getSliderValue();

        // verify last slider value is < starting point; expect result "3"
        assertEquals(lastSliderVal, "3");

    }

}
