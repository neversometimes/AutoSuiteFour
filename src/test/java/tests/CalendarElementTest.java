package tests;

import base.BaseTest;
import pages.CalendarElementPage;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CalendarElementTest extends BaseTest{
    String offersPage = "https://rahulshettyacademy.com/seleniumPractise/#/offers";

    @Test
    public void testCalendarElement() {
        // Exercise: set a date using a web calendar element
        //  Task: set date to: 15 June 2027
        //  assert date set is correct after using calendar element

        CalendarElementPage calendarElementPage = new CalendarElementPage(driver);

        String day = "15";
        String month = "June";
        String year = "2027";

        goToURL(offersPage);  // start at target page!!

        // click on calendar text input to bring up calendar date picker
        calendarElementPage.clickCalendarInput();

        // click TWICE on month YYYY header of calendar picker
        calendarElementPage.clickCalendarHeader();
        calendarElementPage.clickCalendarHeader();

        // click on "2027" in calendar year picker
        calendarElementPage.clickCalendarYear(year);

        // click "June" from calendar month picker
        calendarElementPage.clickCalendarMonth(month);

        // click "15" in calendar day picker
        calendarElementPage.clickCalendarDay(day);

        //assertEquals(monthTxt + "/" + dayTxt + "/" + yearTxt, "6/15/2027");
        assertEquals(calendarElementPage.getMonthTxt() + "/"
                + calendarElementPage.getDayTxt() + "/"
                + calendarElementPage.getYearTxt(), "6/15/2027");
    }
}
