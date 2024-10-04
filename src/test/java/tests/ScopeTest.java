package tests;

import base.BaseTest;
import pages.ScopePage;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;
import java.util.Set;

public class ScopeTest extends BaseTest {
    String practicePage = "https://qaclickacademy.com/practice.php";

    @Test
    public void testScope() throws Exception{
        // 1) get total links count on the page
        // 2) get links count only within the footer section (two ways)
        // 3) get links count for footer 1st column only
        // 4) follow each link in #3, verify nav to correct page

        ScopePage scopePage = new ScopePage(driver);
        goToURL(practicePage);

      // #1 - full page scope
        // get count of all hyperlinks on the page
        assertEquals(scopePage.getCountOfAllAnchors(), 27);

      //#2a - isolating scope using CSS (my solution)
        // verify count of footer links
        assertEquals(scopePage.getCountOfFooterAnchorsCSS(), 20);

      //#2b - isolating scope using WebElement driver
        // verify count of footer links (another option)
        assertEquals(scopePage.getCountOfFooterAnchors(), 20);

      //#3
        // verify count of footer links - first column only
        assertEquals(scopePage.getCountOfFooterAnchorsFirstColumn(), 5);

      //#4
        // get the footer column links into a List
        List<WebElement> columnAnchors = scopePage.getColumnAnchors();

        // get the count of footer column links
        int aTagCount = scopePage.getCountOfFooterAnchorsFirstColumn();

        // follow all footer column links
        scopePage.navigateColumnAnchors(columnAnchors, aTagCount);

        //fetch handles of all browser windows
        Set<String> setOfWindows = scopePage.getWindowHandles(); // expect 5 : includes home test page

        // verify for each window w from set of Windows that its URL is found in target URL list
        for (String w : setOfWindows) { // starts at [0]
            scopePage.switchWindow(w);
            assertTrue(scopePage.getTargetUrlList().stream().anyMatch(s -> getPageURL().contains(s)));
        }
    }

}
