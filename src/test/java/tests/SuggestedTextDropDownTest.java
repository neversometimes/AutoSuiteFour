package tests;

import base.BaseTest;
import pages.SuggestedTextDropDownPage;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;



public class SuggestedTextDropDownTest extends BaseTest {
    String practicePage = "https://rahulshettyacademy.com/AutomationPractice/";


    @Test
    public void autoSuggestTest() throws Exception {
        SuggestedTextDropDownPage stddPage = new SuggestedTextDropDownPage(driver);
        goToURL(practicePage);

        //type "uni" into suggestion textbox
        stddPage.addTextToSuggestInput("uni");

        // add all dropdown suggestion options to suggestList List
        List<WebElement> suggestList = stddPage.getDropDownOptions();


        // filter suggestlist to only "United States (USA)" option
        List<WebElement> filteredSuggestList = stddPage.filterSuggestList("United States (USA)");

        // get the first (now only) filtered item and click it
        stddPage.clickFilteredItem(filteredSuggestList);

        //verify United States (USA) appears in the textbox
        assertEquals(stddPage.getSuggestedText(), "United States (USA)");
    }

}
