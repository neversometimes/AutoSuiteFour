package tests;

import base.BaseTest;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import pages.MouseActionsPage;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;


public class MouseActionsTest extends BaseTest {
    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    String dropDownPage = "https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html";
    String mouseOverPage = "https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html";
    String dragNdropPage = "https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html";
    String canvasPage = "https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html";

    @Test
    public void simpleWebNav(){
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);
        goToURL(initPage);

        //very basic web page navigation using xpath link text and CLICK()
        mouseActionsPage.clickNavBtn();
        mouseActionsPage.clickThreeBtn();
        mouseActionsPage.clickTwoBtn();
        mouseActionsPage.clickNextBtn();
        mouseActionsPage.clickPrevBtn();
        mouseActionsPage.clickOneBtn();

        assertTrue(mouseActionsPage.getBodyText().contains("Lorem ipsum"));
    }
    @Test
    public void checkboxAndRadioBtns () {
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);
        goToURL(webFormPage);

        assertFalse(mouseActionsPage.isCheckbox2Selected());  // verify init checkbox state is not selected

        mouseActionsPage.clickChkbox2();
        assertTrue(mouseActionsPage.isCheckbox2Selected());  // verify default checkbox is now selected

        assertTrue(mouseActionsPage.isRadio1Selected());        // verify initial Radio1 state is selected
        assertFalse(mouseActionsPage.isRadio2Selected());       // verify initial Radio2 is not selected

        // .click() seems to require the control to be visible - this was intermittent
        mouseActionsPage.clickRadio2();

        assertFalse(mouseActionsPage.isRadio1Selected());        // verify now Radio1 state is not selected
        assertTrue(mouseActionsPage.isRadio2Selected());       // verify now Radio2 is selected

    }

    @Test
    public void contextAndDoubleClick(){
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);
        goToURL(dropDownPage);

        mouseActionsPage.contextClickDropdown2();
        assertTrue(mouseActionsPage.isContextMenu2Displayed());

        mouseActionsPage.doubleClickDropdown3();
        assertTrue(mouseActionsPage.isContextMenu3Displayed());
    }

    @Test
    public void testMouseOver() {
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);
        goToURL(mouseOverPage);

        List<String> captionList = Arrays.asList("compass", "calendar", "award", "landscape");

        for (String captionName : captionList) {

            // for each captionList name, get image WebElement object
            WebElement image = mouseActionsPage.getImg(captionName);

            // verify image caption from captionList name
            assertTrue(captionName.equalsIgnoreCase(mouseActionsPage.getImgCaption(image)));

        }
    }
    @Test
    public void dragAndDrop() {
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);
        int offset = 250;

        goToURL(dragNdropPage);

        WebElement srcElement = mouseActionsPage.getSrcElement();

        // get initial src location
        Point initSrcLocation = mouseActionsPage.getSrcLocation();

        // configure drag and drop offset by
        mouseActionsPage.configOffset(srcElement, offset);

        // verify no change in src location from initial
        assertEquals(mouseActionsPage.getSrcLocation(), initSrcLocation );

        WebElement tarElement = mouseActionsPage.getTarElement();

        // do the actual drag and drop mouse action
        mouseActionsPage.dragAndDrop(srcElement, tarElement);

        // verify src location is now = target location
        assertEquals(mouseActionsPage.getSrcLocation(), mouseActionsPage.getTarLocation());

    }

    @Test
    public void clickAndHoldToDraw() {
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);

        goToURL(canvasPage);

        Actions actions = new Actions(driver);

        mouseActionsPage.setFocusOnCanvas(actions);

        mouseActionsPage.drawCircle(actions);

        mouseActionsPage.releaseCanvasFocus(actions);
    }

    @Test
    public void copyAndPaste() {
        // NOTE: SystemUtils doesn't work with RemoteWebDriver. Gets hub properties and not node properties
        MouseActionsPage mouseActionsPage = new MouseActionsPage(driver);

        // modifier for cross-platform keyboard variance CTRL / CMD keys
        Keys modifier = SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;

        goToURL(webFormPage);

        mouseActionsPage.copyAndPasteInputToArea(modifier);

        // verify the copy-paste action; string at target textArea matches string from source textInput
        assertEquals( mouseActionsPage.getTextAreaTxt(), mouseActionsPage.getTextInputTxt());
    }

}
