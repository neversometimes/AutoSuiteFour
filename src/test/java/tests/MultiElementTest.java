package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.MultiElementPage;

import static org.testng.Assert.*;

public class MultiElementTest extends BaseTest {
    String practicePage = "https://rahulshettyacademy.com/AutomationPractice/";

    @Test
    public void multipleElements() {
        // practice exercise - don't hardcode any label strings - use only variables
        // 1. at practice website, select (check) one of the checkboxes (Option3)
        // 2. get label from that checkbox and select that from dropdown control
        // 3. enter text from dropdown into textbox
        // 4. click 'Alert' button
        // 5. verify alert button text contains text from checkbox in step 2
        MultiElementPage multiElementPage = new MultiElementPage(driver);
        goToURL(practicePage);

        //  #1
        multiElementPage.clickOption3Chkbox();

        //  #2
        multiElementPage.selectDropDownOfChckboxLabel();
        String dropDownLabelTxt = multiElementPage.getFirstSelectedOption();

        //  #3
        multiElementPage.enterNameInAlertTextInput(dropDownLabelTxt);

        //  #4
        multiElementPage.clickAlertBtn();

        //  #5
        assertEquals(multiElementPage.getAlertTxt(), "Hello " + dropDownLabelTxt
                + ", share this practice page and share your knowledge");
    }

}
