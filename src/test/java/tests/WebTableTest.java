package tests;

import base.BaseTest;
import pages.WebTablePage;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;


public class WebTableTest extends BaseTest {
    String practicePage = "https://rahulshettyacademy.com/AutomationPractice/";

    // Exercise:  the following test was an exercise to count the rows and columns of a web table,
    //              then print out the data found in the 2nd row.
    @Test
    public void tableRowCountTest() {
        WebTablePage webTablePage = new WebTablePage(driver);

        goToURL(practicePage);

        // verify Web Table Row Count
        assertEquals(webTablePage.getWebTableRowCount(), 11);

        // verify Web Table Column Count
        assertEquals(webTablePage.getWebTableColumnCount(), 3);

        // get Web Table 2nd Row values
        List<WebElement> webTable2ndRow = webTablePage.getWebTable2ndRow();

        // verify each item in the 2nd row vs. expected target Row values list
        for (WebElement webElement : webTable2ndRow) {
            assertTrue(webTablePage.getTargetRowValues().stream().
                    anyMatch(s -> webElement.getText().contains(s)));

            //System.out.println(webElement.getText());
        }

    }

}
