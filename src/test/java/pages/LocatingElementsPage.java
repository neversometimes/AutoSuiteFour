package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.List;

public class LocatingElementsPage {

    WebDriver driver;
    WebElement readOnlyElement;
    List<WebElement> rowsInForm;

    public LocatingElementsPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "textarea")
    WebElement textArea;
    @FindBy(linkText = "Return to index")
    WebElement linkTxt;
    @FindBy(partialLinkText = "index")
    WebElement partialLinkTxt;
    @FindBy(name = "my-text")
    WebElement byName;
    @FindBy(id = "my-text-id")
    WebElement byID;
    @FindBy(className = "form-control")
    List<WebElement> elementsList;
    @FindBy(css = "input[type=hidden]")
    WebElement hiddenCSSElement;
    @FindBy(css = "[type=checkbox]:checked")
    WebElement checkBx1;
    @FindBy(css = "[type=checkbox]:not(:checked)")
    WebElement checkBx2;
    @FindBy(xpath = "//input[@type='hidden']")
    WebElement hiddenXpathElement;
    @FindBy(xpath = "//*[@type='radio' and @checked]")
    WebElement radioBtn1;
    @FindBy(xpath = "//*[@type='radio' and not(@checked)]")
    WebElement radioBtn2;
    @FindBy(how = How.ID_OR_NAME, using = "my-file")
    WebElement byIDorName;
    @FindBy(name = "my-date")
    WebElement datePicker;


    //  ***********   PAGE METHODS   **************
    public String getTextareaRowCount() {
        return textArea.getDomAttribute("rows");
    }
    public String getTagFromLinkTxt() {
        return linkTxt.getTagName();
    }
    public String getPropertyFromLinktxt() {
        return linkTxt.getCssValue("cursor");
    }
    public Point getLocByPartialLinkTxt() {
        return partialLinkTxt.getLocation();
    }
    public Point getLocByLinkTxt() {
        return linkTxt.getLocation();
    }
    public Rectangle getRectByLinkTxt() {
        return linkTxt.getRect();
    }
    public Rectangle getRectByPartialLinkTxt() {
        return partialLinkTxt.getRect();
    }
    public boolean isEnabledByName() {
        return byName.isEnabled();
    }
    public String getAttributeByID(String type) {
        return byID.getAttribute(type);
    }
    public String getDomAttributeByID(String type) {
        return byID.getDomAttribute(type);
    }
    public String getDomPropertyByID(String type) {
        return byID.getDomProperty(type);
    }
    public boolean isElementListEmpty() {
        return elementsList.isEmpty();
    }
    public int getElementListSize() {
        return elementsList.size();
    }
    public String getFirstListElementNameAttribute() {
        return elementsList.getFirst().getAttribute("name");
    }
    public String getSecondListElementNameAttribute() {
        return elementsList.get(1).getAttribute("name");
    }
    public boolean isCSSElementDisplayed() {
        return hiddenCSSElement.isDisplayed();
    }
    public String getFirstCheckboxIDAttribute() {
        return checkBx1.getAttribute("id");
    }
    public boolean isFirstCheckbxSelected() {
        return checkBx1.isSelected();
    }
    public boolean isSecondCheckbxSelected() {
        return checkBx2.isSelected();
    }
    public String getSecondCheckboxIDAttribute() {
        return checkBx2.getAttribute("id");
    }
    public boolean isXpathElementDisplayed() {
        return hiddenXpathElement.isDisplayed();
    }
    public String getRadioBtn1IDAttribute() {
        return radioBtn1.getAttribute("id");
    }
    public String getRadioBtn2IDAttribute() {
        return radioBtn2.getAttribute("id");
    }
    public boolean isRadioBtn1Selected() {
        return radioBtn1.isSelected();
    }
    public boolean isRadioBtn2Selected() {
        return radioBtn2.isSelected();
    }
    public boolean isFileElementIDAttributeBlank() throws Exception {
        return byIDorName.getAttribute("id").isBlank();     // no id='my-file', so attrib is blank
    }
    public boolean isFileElementNameAttributeBlank() throws Exception {
        return byIDorName.getAttribute("name").isBlank();   // name = 'my-file', so attrib not blank
    }
    public int getListElementSizeByChained() {
        rowsInForm = driver.findElements(new ByChained(By.tagName("form"), By.className("row")));
        return rowsInForm.size();
    }
    public int getListElementSizeByAll() {
        rowsInForm = driver.findElements(new ByAll(By.tagName("form"), By.className("row")));
        return rowsInForm.size();
    }
    public String getReadOnlyAttributeAboveLink() {
        // relativeBy.above() doesn't work with page factory locators - must use By
        WebElement link = driver.findElement(By.linkText("Return to index"));

        RelativeLocator.RelativeBy relativeBy = RelativeLocator.with(By.tagName("input"));
        readOnlyElement = driver.findElement(relativeBy.above(link));
        return readOnlyElement.getAttribute("name");
    }
    public WebElement clickDatePicker() {
        datePicker.click();
        return datePicker;
    }
    public WebElement clickCurrentMonth(int year) {
        WebElement monthElement = driver.findElement(By.xpath(String.format("//th[contains(text(), '%d')]", year)));
        monthElement.click();
        return monthElement;
    }
    public WebElement clickDatePickerLeftArrow(WebElement month) {
        WebElement arrowLeft = driver.findElement(RelativeLocator.with(By.tagName("th")).toRightOf(month));
        arrowLeft.click();
        return arrowLeft;
    }
    public void clickCurrentMonthOfYear(WebElement arrowLeft) {
        WebElement monthPastYear = driver.findElement(RelativeLocator.with
                (By.cssSelector("span[class$=focused")).below(arrowLeft));
        monthPastYear.click();
    }
    public void clickPresentDayOfMonth(int day) {
        WebElement dayElement = driver.findElement(By.xpath(String.format("//td[@class='day' and contains(text(), '%d')]", day)));
        dayElement.click();
    }
    public String getDatePickerDate(WebElement finalDate) {
        return finalDate.getAttribute("value");
    }



}
