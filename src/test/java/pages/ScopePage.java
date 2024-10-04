package pages;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScopePage {

    WebDriver driver;
    Wait<WebDriver> wait;
    JavascriptExecutor js;

    public ScopePage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.js = (JavascriptExecutor) driver;
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "a")
    List<WebElement> anchors;
    @FindBy(css = ".gf-t a")
    List<WebElement> footerAnchors;
    @FindBy(id = "gf-BIG")
    WebElement footerDriver;

    //  ***********   PAGE METHODS   **************
    public int getCountOfAllAnchors() {
        return anchors.size();
    }
    public int getCountOfFooterAnchorsCSS() {
        return footerAnchors.size();
    }
    public int getCountOfFooterAnchors() {
        return footerDriver.findElements(By.tagName("a")).size();
    }
    public int getCountOfFooterAnchorsFirstColumn() {
        WebElement firstColDriver = footerDriver.findElement(By.cssSelector("table tbody tr td:first-child ul"));
        return firstColDriver.findElements(By.tagName("a")).size();
    }
    public List<WebElement> getColumnAnchors() {
        WebElement firstColDriver = footerDriver.findElement(By.cssSelector("table tbody tr td:first-child ul"));
        return firstColDriver.findElements(By.tagName("a"));
    }
    public void navigateColumnAnchors(List<WebElement> hLinkList, int hLinkCount) throws Exception{

        // added this scrollIntoView() javascript in order to work on FireFox
        String scrollScript = "arguments[0].scrollIntoView();";
        js.executeScript(scrollScript, footerDriver);

        // modifier for cross-platform keyboard variance CTRL / CMD keys
        Keys cmdKey = SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;

        Actions action = new Actions(driver);

        for (int i=1; i<hLinkCount; i++) {
            // control click each link to open new tabs
            action.keyDown(cmdKey)
                    .pause(Duration.ofSeconds(1))
                    .click(hLinkList.get(i))        // ctrl-click each hlink in list
                    .keyUp(cmdKey)
                    .build()
                    .perform();

            Thread.sleep(500);
        }
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }
    public List<String> getTargetUrlList() {
        List<String> ls = new ArrayList<>();
        ls.add("rahulshettyacademy.com");
        ls.add("www.udemy.com");
        ls.add("www.soapui.org");
        ls.add("www.restapitutorial.com");
        ls.add("jmeter.apache.org");

        return ls;
    }

    public void switchWindow(String w) throws Exception{
        driver.switchTo().window(w);
        Thread.sleep(500);
    }
}
