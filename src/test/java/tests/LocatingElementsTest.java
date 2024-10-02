package tests;

import base.BaseTest;
import pages.LocatingElementsPage;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LocatingElementsTest extends BaseTest {

    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @Test
    public void tagName(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        // get number of rows in textarea web element
        String rowCount = locatingElementsPage.getTextareaRowCount();
        assertEquals(rowCount, "3");
    }
    @Test
    public void linkText(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        //get tag name from link text
        assertEquals(locatingElementsPage.getTagFromLinkTxt(), "a");

        // get property by link text
        assertEquals(locatingElementsPage.getPropertyFromLinktxt(), "pointer");

        // get location by partial link text - confirm equals get location by link text
        assertEquals(locatingElementsPage.getLocByPartialLinkTxt(), locatingElementsPage.getLocByLinkTxt());

        // get rect by partial link text - confirm equals get rect by link text
        assertEquals(locatingElementsPage.getRectByPartialLinkTxt(), locatingElementsPage.getRectByLinkTxt());
    }
    @Test
    public void htmlAttributes(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        String myType = "type"; String myProp = "myprop";
        goToURL(webFormPage);

        //verify is enabled by name
        assertTrue(locatingElementsPage.isEnabledByName());

        //verify attribute, DOM attribute, property by id
        assertEquals(locatingElementsPage.getAttributeByID(myType), "text");
        assertEquals(locatingElementsPage.getDomAttributeByID(myType), "text");
        assertEquals(locatingElementsPage.getDomPropertyByID(myType), "text");

        assertEquals(locatingElementsPage.getAttributeByID(myProp), "myvalue");
        assertEquals(locatingElementsPage.getDomAttributeByID(myProp), "myvalue");
        assertNull(locatingElementsPage.getDomPropertyByID(myProp));

        //count # of "form-control" elements on the page  and getAttribute by class name
        assertFalse(locatingElementsPage.isElementListEmpty());
        assertEquals(locatingElementsPage.getElementListSize(), 9);  // 9 form controls on the page
        assertEquals(locatingElementsPage.getFirstListElementNameAttribute(), "my-text");
        assertEquals(locatingElementsPage.getSecondListElementNameAttribute(), "my-password");

    }
    @Test
    public void cssSelector(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        // basic locator with CSS selector
        assertFalse(locatingElementsPage.isCSSElementDisplayed());

        // advanced locators with CSS selectors
        assertEquals(locatingElementsPage.getFirstCheckboxIDAttribute(), "my-check-1");
        assertTrue(locatingElementsPage.isFirstCheckbxSelected());

        assertEquals(locatingElementsPage.getSecondCheckboxIDAttribute(), "my-check-2");
        assertFalse(locatingElementsPage.isSecondCheckbxSelected());

    }
    @Test
    public void xpath(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        // basic locator with XPath
        assertFalse(locatingElementsPage.isXpathElementDisplayed());

        // advanced locators with XPath
        assertEquals(locatingElementsPage.getRadioBtn1IDAttribute(), "my-radio-1");
        assertTrue(locatingElementsPage.isRadioBtn1Selected());

        assertEquals(locatingElementsPage.getRadioBtn2IDAttribute(), "my-radio-2");
        assertFalse(locatingElementsPage.isRadioBtn2Selected());

    }
    @Test
    public void idOrName() throws Exception {
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        assertTrue(locatingElementsPage.isFileElementIDAttributeBlank());
        assertFalse(locatingElementsPage.isFileElementNameAttributeBlank());
    }

    @Test
    public void compoundComplexChained(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        assertEquals(locatingElementsPage.getListElementSizeByChained(), 1);
    }

    @Test
    public void compoundByAll(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        assertEquals(locatingElementsPage.getListElementSizeByAll(), 5);
    }

    @Test
    public void basicRelativeLocator(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        assertEquals(locatingElementsPage.getReadOnlyAttributeAboveLink(), "my-readonly");
    }

    @Test
    void datePickerUsingRelativeLocators(){
        LocatingElementsPage locatingElementsPage = new LocatingElementsPage(driver);
        goToURL(webFormPage);

        // get current date from the system clock
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentDay = today.getDayOfMonth();

        // click on the date picker to open calendar
        WebElement datePicker = locatingElementsPage.clickDatePicker();

        // click on the current month by searching html tag attributes
        WebElement monthElement = locatingElementsPage.clickCurrentMonth(currentYear);

        // click on the left arrow using relative locators
        WebElement arrowLeft = locatingElementsPage.clickDatePickerLeftArrow(monthElement);

        // click on the current month of that year
        locatingElementsPage.clickCurrentMonthOfYear(arrowLeft);

        // click on the present day in that month
        locatingElementsPage.clickPresentDayOfMonth(currentDay);

        // get final date on the input text
        String oneYearBack = locatingElementsPage.getDatePickerDate(datePicker);

        // verify date is equal to the one selected in date picker
        LocalDate previousYear = today.minusYears(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expectedDate = previousYear.format(dateFormat);
        assertEquals(oneYearBack, expectedDate);
    }
}
