package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.BrowserAgnosticPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.*;
import static org.testng.AssertJUnit.assertEquals;

public class BrowserAgnosticTest extends BaseTest {

    String longPage = "https://bonigarcia.dev/selenium-webdriver-java/long-page.html";
    String scrollScript = "arguments[0].scrollIntoView();";
    String infiniteScrollPage = "https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html";
    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    String colorAttrScript = "arguments[0].setAttribute('value', '%s');";
    String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
    String timedotgovPage = "https://www.time.gov/";
    OutputType<File> pngFile = OutputType.FILE;
    OutputType<String> base64File = OutputType.BASE64;
    String pageSSFileName = "testPageScreenshot.png";
    String webElementSSFileName = "testWebelementScreenshot.png";
    String shadowDomPage = "https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html";
    String cookiesPage = "https://bonigarcia.dev/selenium-webdriver-java/cookies.html";
    String cookieName = "new-cookie-key";
    String cookieValue = "new-cookie-value";


    @Test
    public void scrollByPixels() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(longPage);

        String scriptDown = "window.scrollBy(0, 5000);";  //scrolls down by x,y pixels
        String scriptUp = "window.scrollBy(0, -5000);";   //scrolls up by x,y pixels

        browserAgnosticPage.jse.executeScript(scriptDown);  // scroll down
        assertTrue(browserAgnosticPage.copyrightDisplayed()); // verify scroll to bottom page copyright

        browserAgnosticPage.jse.executeScript(scriptUp);  // scroll up
        assertTrue(browserAgnosticPage.headerDisplayed());  // verify scroll back to top page header

    }
    @Test
    public void scrollIntoView() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(longPage);

        browserAgnosticPage.jse.executeScript(scrollScript, browserAgnosticPage.getLastParagraph());
        assertTrue(browserAgnosticPage.copyrightDisplayed());
    }
    @Test
    public void infiniteScroll() {
         // INFO: infinite page initially has 20 paragraphs
        //  With each scroll to the bottom, another 20 paragraphs are scrolled
        //  verification is paragraph count after each subsequent scroll

        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(infiniteScrollPage);

        for (int i=1; i<6; i++) {       // scroll 5 times down the page, 20 paragraphs per scroll
            // get paragraph count
            int pNum = browserAgnosticPage.getParagraphCount();

            // verify number of paragraphs scrolled (20x per scroll after initially 20 on page load)
            assertEquals(20*i, pNum);

            // using javascript, scroll to the "current" last paragraph on the page
            browserAgnosticPage.jse.executeScript(scrollScript, browserAgnosticPage.getLastParagraph());

            // wait until there are more paragraphs available on the page
            browserAgnosticPage.waitForParagraphs(pNum);
        }
    }

    @Test
    public void colorPicker() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(webFormPage);

        // get init color of colorPicker
        String initColor = browserAgnosticPage.getClrPkrColor();

        Color red = new Color(255, 0, 0, 1);
        String redHexVal = red.asHex();  // convert Color to String

        // set colorPicker attribute = RED
        String jscript = String.format(colorAttrScript, red.asHex());  // set full js string
        browserAgnosticPage.jse.executeScript(jscript, browserAgnosticPage.getClrPkr()); // js to set clr pkr = red

        // get updated and current color of colorPicker
        String finalColor = browserAgnosticPage.getClrPkrColor();
        //  and... verify it's not the same and that it's now set to RED
        assertNotSame(finalColor, initColor);  // check color changed
        assertEquals(red, Color.fromString(finalColor));  // check color changed to red

    }
    @Test
    public void pinnedScripts() throws IOException {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(initPage);

        ScriptKey linkKey = browserAgnosticPage.jse.pin("return document.getElementsByTagName('a')[2];");  //attach 1st JS fragment
        ScriptKey firstArgkey = browserAgnosticPage.jse.pin("return arguments[0];");  //attach 2nd JS fragment

        Set<ScriptKey> pinnedScripts = browserAgnosticPage.jse.getPinnedScripts();
        assertEquals(pinnedScripts.size(), 2);

        WebElement formLink = (WebElement) browserAgnosticPage.jse.executeScript(linkKey);
        formLink.click();
        assertNotSame(driver.getCurrentUrl(), initPage);

        String message = "Hello Earth!";
        String executeScript = (String) browserAgnosticPage.jse.executeScript(firstArgkey, message);
        assertEquals(executeScript, message);

        browserAgnosticPage.jse.unpin(linkKey);
        assertEquals(browserAgnosticPage.jse.getPinnedScripts().size(), 1);
    }

    @Test
    public void asyncScript() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(initPage);

        Duration pause = Duration.ofSeconds(5);  // 5 second pause time for the JS to be run
        String script = "const callback = arguments[arguments.length - 1];"
                + "window.setTimeout(callback, " + pause.toMillis() + ");";

        long initMills = System.currentTimeMillis();
        browserAgnosticPage.jse.executeAsyncScript(script);  // ASYNC JS execution call

        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMills);  // measure time elasped

        assertTrue(elapsed.toMillis() >= pause.toMillis());  // assert True if elapsed time >= pause time
    }

    @Test
    public void pageScreenShotPng() throws IOException {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(timedotgovPage);

        // take screenshot PNG default file type
        File screenshotFile = browserAgnosticPage.takeFileScreenShot(pngFile);

        //find file destination path
        Path destinationPath = browserAgnosticPage.getFilePath(pageSSFileName);

        //replace existing ss file
        browserAgnosticPage.replaceFile(screenshotFile.toPath(), destinationPath);

        //assert the screenshot was created
        assertNotNull(destinationPath);
    }

    @Test
    public void pageScreenShotBase64() throws IOException {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(timedotgovPage);

        // STRING VAR screenshot in Base64
        String screenshot = browserAgnosticPage.takeBase64ScreenShot(base64File);

        // ** paste this console output result into a browser to verify:
        // System.out.println ("data:image/png;base64," + screenshot);

        assertNotNull(screenshot);  // data capture successful
    }

    @Test
    public void webElementScreenShot() throws IOException {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(timedotgovPage);

        // locate Mtn Time Clock web element on time.gov page
        WebElement clock = browserAgnosticPage.getMtnTimeClock();

        // take web element screenshot PNG default file type
        File screenshotFile = browserAgnosticPage.takeWebElementFileScreenshot(clock, pngFile);

        //find file destination path
        Path destinationPath = browserAgnosticPage.getFilePath(webElementSSFileName);

        //replace existing ss file
        browserAgnosticPage.replaceFile(screenshotFile.toPath(), destinationPath);

        assertNotNull(destinationPath); //assert the screenshot was created
    }

    @Test
    public void browserWindow() {
        goToURL(initPage);

        WebDriver.Window window = driver.manage().window();
        Point initialPosition = window.getPosition();     // grab initial position and size of browser window
        Dimension initialSize = window.getSize();

        window.maximize();  // maximize browser window

        Point maximizedPosition = window.getPosition();   // grab max browser window position and size
        Dimension maximizedSize = window.getSize();

        assertNotSame(initialPosition, maximizedPosition);  // check window position changed
        assertNotSame(initialSize, maximizedSize);    // check window size changed

        window.minimize();    // minimize browser window
        window.fullscreen();  // return to full screen window (not max/not min)
    }
    @Test
    public void browserHistory() {

        String firstPage = initPage + "navigation1.html";
        String secondPage = initPage + "navigation2.html";
        String thirdPage = initPage + "navigation3.html";

        goToURL(firstPage);
        assertEquals(driver.getCurrentUrl(), firstPage);
        driver.navigate().to(secondPage);
        assertEquals(driver.getCurrentUrl(), secondPage);
        driver.navigate().to(thirdPage);
        assertEquals(driver.getCurrentUrl(), thirdPage);
        driver.navigate().back();
        assertEquals(driver.getCurrentUrl(), secondPage);
        driver.navigate().forward();
        assertEquals(driver.getCurrentUrl(), thirdPage);
        driver.navigate().back();
        assertEquals(driver.getCurrentUrl(), secondPage);
        driver.navigate().back();
        assertEquals(driver.getCurrentUrl(), firstPage);
        driver.navigate().forward();
        assertEquals(driver.getCurrentUrl(), secondPage);
        driver.navigate().refresh();
        assertEquals(driver.getCurrentUrl(), secondPage);
    }
    @Test
    public void shadowDOM() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(shadowDomPage);

        //get shadow DOM host element
        WebElement content = browserAgnosticPage.getShadowHost();

        //get shadow DOM root
        SearchContext shadowRoot = browserAgnosticPage.getShadowRootFromHost(content);

        //use the shadow DOM root to find first text element;
        WebElement textElement = browserAgnosticPage.getElementFromShadowRoot(shadowRoot);

        // verify text of element found within the shadow DOM
        assertTrue(textElement.getText().contains("Hello Shadow DOM"));

    }
    @Test
    public void createAndDeleteCookie() {  // CREATE COOKIE and DELETE COOKIE
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(cookiesPage);

        WebDriver.Options options = driver.manage();

        // ** create new cookie and add to web page **
        Cookie testCookie = browserAgnosticPage.addNewCookieToPage(cookieName, cookieValue, options);

        // verify new test cookie value is correct using webdriver.options getCookieNamed method
        assertEquals(testCookie.getValue(), browserAgnosticPage.getCookieValue(testCookie, options));

        // click Display Cookies button
        browserAgnosticPage.clickDisplayCookiesBtn();
        //verify new added test cookie and value are displayed
        assertTrue(browserAgnosticPage.getDisplayedCookieList().contains(cookieName));

        //  ** delete the test cookie **
        browserAgnosticPage.deleteCookie(testCookie, options);

        // click Display Cookies button
        browserAgnosticPage.clickDisplayCookiesBtn();
        // verify the added test cookie doesn't display (after being deleted)
        assertFalse(browserAgnosticPage.getDisplayedCookieList().contains(cookieName));
    }

    @Test
    public void countAndReadValuesOfCookies(){     // COUNT and READ VALUES of EXISTING COOKIES
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(cookiesPage);

        WebDriver.Options options = driver.manage();

        // get and count cookies
        Set<Cookie> cookies = options.getCookies();  // read all cookies of the page
        assertEquals(2, cookies.size());  // default page has 2 cookies

        // read cookie value by name
        Cookie usernameCookie = options.getCookieNamed("username");  // get username cookie
        assertEquals("John Doe", browserAgnosticPage.getCookieValue(usernameCookie, options));  // verify value of cookie

    }
    @Test
    public void updateCookie() {   //  UPDATE EXISTING COOKIE VALUE
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(cookiesPage);

        WebDriver.Options options = driver.manage();
        // update value of existing username cookie
        Cookie editedCookie = browserAgnosticPage.updateCookie(options.getCookieNamed("username"), options);
        // verify value of edited cookie is updated
        assertEquals(editedCookie.getValue(), options.getCookieNamed("username").getValue());

        // click Display Cookies button
        browserAgnosticPage.clickDisplayCookiesBtn();
        // verify cookie values includes new updated value
        assertTrue(browserAgnosticPage.getDisplayedCookieList().contains("new-value"));
    }

    @Test
    public void selectDropDownListByText() {
        String optionLabel = "Three";
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(webFormPage);

        // select dropdown list item by name
        Select select = browserAgnosticPage.selectDropDown();    // find dropdown select

        // select option labeled "Three"
        browserAgnosticPage.selectOptionByLabel(select, optionLabel);

        // verify default item displayed == "Three" option in dropdown
        assertEquals(optionLabel, browserAgnosticPage.getFirstDropDownItemTxt(select));
    }
    @Test
    public void testDataList() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);
        goToURL(webFormPage);

        WebElement dataList = driver.findElement(By.name("my-datalist")); //find datalist web element
        dataList.click();  // mouse click datalist to set focus in web element control

        WebElement option = driver
                .findElement(By.xpath("//datalist/option[2]")); // find 2nd option in list
        String optionValue = option.getAttribute("value"); // get 2nd option value ("New York")
        dataList.sendKeys(optionValue);  // enter "New York" to select that option of dropdown

        assertEquals(optionValue, "New York");  // verify text selected and typed is "New York"

    }
    @Test
    public void testOpenNewTab() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        String initURL = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(initURL);
        String initHandle = driver.getWindowHandle();  // get window handle

        driver.switchTo().newWindow(WindowType.TAB);  // open new TAB
        String nextURL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
        driver.get(nextURL);  // open new page in TAB
        assertEquals(driver.getWindowHandles().size(), 2); // 2 window handles currently

        String nextHandle = driver.getWindowHandle(); // grab handle of new TAB window

        driver.switchTo().window(initHandle);  // switch back to original window
        driver.close();                         // close original browser window
        assertEquals(driver.getWindowHandles().size(), 1);  // verify window handle count == 1

        driver.switchTo().window(nextHandle);   // switch driver to TAB window via handle
        assertEquals(driver.getCurrentUrl(), nextURL); // verify current URL == TAB window URL
    }
    @Test
    public void testOpenNewWindow() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        String initURL = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(initURL);
        String initHandle = driver.getWindowHandle();  // get window handle

        driver.switchTo().newWindow(WindowType.WINDOW);  // open new WINDOW
        String nextURL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
        driver.get(nextURL);  // open new page in NEW WINDOW
        assertEquals(driver.getWindowHandles().size(), 2); // 2 window handles currently

        String nextHandle = driver.getWindowHandle(); // grab handle of new WINDOW

        driver.switchTo().window(initHandle);  // switch back to original window
        driver.close();                         // close original browser window
        assertEquals(driver.getWindowHandles().size(), 1);  // verify window handle count == 1

        driver.switchTo().window(nextHandle);   // switch driver to NEW WINDOW via handle
        assertEquals(driver.getCurrentUrl(), nextURL); // verify current URL == NEW WINDOW URL
    }
    @Test
    public void testiFrames() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // wait 10s for iFrame to load
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));

        By pName = By.tagName("p");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0)); // waiting for paragraphs
        List<WebElement> paragraphs = driver.findElements(pName); // find all paragraphs in iFrame
        assertEquals(paragraphs.size(), 20); // verify 20 paragraphs found in iFrame
    }
    @Test
    public void testFrames() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/frames.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // wait 10s max
        String frameName = "frame-body";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(frameName))); // wait until found & loaded

        driver.switchTo().frame(frameName);  // switch to frame body element

        By pName = By.tagName("p");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0)); // wait to load paragraphs
        List<WebElement> paragraphs = driver.findElements(pName);  // find all paragraphs in FRAME
        assertEquals(paragraphs.size(), 20);  // verify 20 paragraphs found in FRAME
    }
    @Test
    public void testAlerts() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-alert")).click();   // click alert button web element
        wait.until(ExpectedConditions.alertIsPresent()); // wait for alert to appear
        Alert alert = driver.switchTo().alert();         // put focus on alert UI

        assertEquals(alert.getText(), "Hello world!"); // verify alert UI text

        alert.accept();   // click OK on alert to dismiss
    }
    @Test
    public void testConfirms() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-confirm")).click();  // click confirm web element
        wait.until(ExpectedConditions.alertIsPresent());  // wait for confirm to appear
        Alert confirm = driver.switchTo().alert();        // set focus on confirm UI

        assertEquals(confirm.getText(), "Is this correct?"); // verify confirm UI text

        confirm.dismiss();  //click Cancel
        // cancel displays "You chose: false" text below web element on page
        // TO DO:  add assert to verify?
    }
    @Test
    public void testPrompts() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-prompt")).click();  // click prompt web element
        wait.until(ExpectedConditions.alertIsPresent()); // wait for prompt to appear
        Alert prompt = driver.switchTo().alert();        // set focus on prompt UI

        assertEquals(prompt.getText(), "Please enter your name"); // verify prompt UI text

        prompt.sendKeys("John Doe"); // text input on prompt
        prompt.accept();

        // OK on prompt sets text below web element on the page = "You typed: John Doe"
        // cancel on prompt sets text below web element on the page = "You typed: null"
        // add assert to verify?
    }
    @Test
    public void testModals() {
        BrowserAgnosticPage browserAgnosticPage = new BrowserAgnosticPage(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-modal")).click(); // click modal web element
        WebElement close = driver
                .findElement(By.xpath("//button[text() = 'Close']")); // find modal Close button

        assertEquals(close.getTagName(), "button"); // verify tag name on Close button

        wait.until(ExpectedConditions.elementToBeClickable(close));  // wait for modal to be clicked
        close.click();  // click modal close button
        // verify other text on modal
        // verify text below modal web element that is returned from modal alert action
    }
/*    @Test
    public void testWebStorage() {

        // WebStorage and SessionStorage objects are deprecated
        // TO DO : rewrite using updated object references

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");
        WebStorage webStorage = (WebStorage) driver;

        SessionStorage sessionStorage = webStorage.getSessionStorage();

        assertEquals(sessionStorage.size(), 2);

        sessionStorage.setItem("new element", "new value");
        assertEquals(sessionStorage.size(), 3);

        driver.findElement(By.id("display-session")).click();
    }
*/




}
