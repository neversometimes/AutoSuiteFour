package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CalendarElementPage {

    WebDriver driver;

    public CalendarElementPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = ".react-date-picker__inputGroup")
    WebElement calendarInput;
    @FindBy(css = "button[class='react-calendar__navigation__label']")
    WebElement calendarHeader;
    @FindBy(css = "input[name*='year']")
    WebElement yearTxt;
    @FindBy(css = "input[name*='month']")
    WebElement monthTxt;
    @FindBy(css = "input[name*='day']")
    WebElement dayTxt;

    //  ***********   PAGE METHODS   **************
    public void clickCalendarInput() {
        calendarInput.click();
    }
    public void clickCalendarHeader() {
        calendarHeader.click();
    }
    public void clickCalendarYear() {
        // click on "2027" in calendar year picker
        driver.findElement(By.cssSelector("div[class*='year'] button:nth-child(7)")).click();
    }
    public void clickCalendarMonth() {
        // click "June" from calendar month picker
        driver.findElement(By.cssSelector("div[class*='months'] button:nth-child(6)")).click();
    }
    public void clickCalendarDay(String day) {
        // click "15" in calendar day picker
        driver.findElement(By.xpath("//button[contains(.,'" + day + "')]")).click();
    }
    public String getYearTxt() {
        return yearTxt.getAttribute("value");
    }
    public String getMonthTxt() {
        return monthTxt.getAttribute("value");
    }
    public String getDayTxt() {
        return dayTxt.getAttribute("value");
    }

}
