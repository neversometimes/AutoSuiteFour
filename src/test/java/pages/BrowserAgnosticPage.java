package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class BrowserAgnosticPage {
    WebDriver driver;
    public JavascriptExecutor jse;
    public TakesScreenshot tss;
    Wait<WebDriver> wait;

    public BrowserAgnosticPage (WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
        this.tss = (TakesScreenshot) driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //  ***********  PAGE FACTORY / LOCATOR DECLARATIONS  *************
    @FindBy(css = "p:last-child")
    WebElement lastParagraph;
    @FindBy(className = "text-muted")
    WebElement copyright;
    @FindBy(css = "h1[class='display-4']")
    WebElement header;
    @FindBy(name = "my-colors")
    WebElement clrPkr;
    @FindBy(css = ".clock-box.green.dst-clock")
    WebElement mtnTimeClock;
    @FindBy(id = "content")
    WebElement shadowHost;
    @FindBy(id = "refresh-cookies")
    WebElement displayCookiesBtn;
    @FindBy(tagName = "p")
    List<WebElement> cookieList;
    @FindBy(name = "my-select")
    WebElement dropDown;


    By para = By.tagName("p");

    //  ***********   PAGE METHODS   **************

    public WebElement getLastParagraph() {
        return lastParagraph;
    }
    public boolean copyrightDisplayed() {
        return copyright.isDisplayed();
    }
    public boolean headerDisplayed() {
        return header.isDisplayed();
    }
    public int getParagraphCount() {
        List<WebElement> paragraphs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(para, 0));
        return paragraphs.size();
    }
    public void waitForParagraphs(int paraCount) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(para, paraCount ));
    }
    public String getClrPkrColor() {
        return clrPkr.getAttribute("value");
    }
    public WebElement getClrPkr() {
        return clrPkr;
    }
    public File takeFileScreenShot(OutputType<File> outputFileType) {
        return tss.getScreenshotAs(outputFileType);
    }
    public String takeBase64ScreenShot(OutputType<String> outputFileType) {
        return tss.getScreenshotAs(outputFileType);
    }
    public File takeWebElementFileScreenshot(WebElement webElement, OutputType<File> outputFileType) {
        return webElement.getScreenshotAs(outputFileType);
    }

    public Path getFilePath(String fileName) {
        return Paths.get(fileName);
    }
    public void replaceFile(Path source, Path target) throws IOException {
        Files.move(source, target, REPLACE_EXISTING);
    }
    public WebElement getMtnTimeClock() {
        return mtnTimeClock;
    }
    public WebElement getShadowHost() {
        return shadowHost;
    }
    public SearchContext getShadowRootFromHost(WebElement shadowHost) {
        return shadowHost.getShadowRoot();
    }

    public WebElement getElementFromShadowRoot(SearchContext root) {
       return root.findElement(By.cssSelector("p"));
    }
    public String getCookieValue(Cookie testCookie, WebDriver.Options options) {
       return Objects.requireNonNull(options.getCookieNamed(testCookie.getName())).getValue();
    }
    public Cookie addNewCookieToPage(String name, String value, WebDriver.Options options) {
        Cookie newCookie = new Cookie(name, value);
        options.addCookie(newCookie);
        return newCookie;
    }
    public void clickDisplayCookiesBtn() {
        displayCookiesBtn.click();
    }
    public void deleteCookie(Cookie cookieName, WebDriver.Options options) {
        options.deleteCookie(cookieName);
    }
    public String getDisplayedCookieList() {
        return cookieList.getLast().getText();
    }
    public Cookie updateCookie(Cookie cookie, WebDriver.Options options) {
        Cookie oreo = new Cookie(cookie.getName(), "new-value");
        options.addCookie(oreo);
        return oreo;
    }
    public Select selectDropDown() {
        return new Select(dropDown);
    }
    public void selectOptionByLabel(Select s, String str) {
        s.selectByVisibleText(str);
    }
    public String getFirstDropDownItemTxt(Select s) {
        return s.getFirstSelectedOption().getText();
    }

}
