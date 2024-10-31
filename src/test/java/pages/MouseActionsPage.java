package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.time.Duration;

public class MouseActionsPage {

    WebDriver driver;

    public MouseActionsPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(css = "nav li:nth-child(4)")
    WebElement threeBtn;
    @FindBy(css = "nav li:nth-child(3)")
    WebElement twoBtn;
    @FindBy(css = "nav li:nth-child(5)")
    WebElement nextBtn;
    @FindBy(css = "nav li:nth-child(1)")
    WebElement prevBtn;
    @FindBy(css = "nav li:nth-child(2)")
    WebElement oneBtn;
    @FindBy(tagName = "body")
    WebElement bodyText;
    @FindBy(id = "my-check-2")
    WebElement checkbx2;
    @FindBy(id = "my-radio-1")
    WebElement radio1;
    @FindBy(id = "my-radio-2")
    WebElement radio2;
    @FindBy(id = "my-dropdown-2")
    WebElement dropdown2;
    @FindBy(id = "context-menu-2")
    WebElement contextMenu2;
    @FindBy(id = "my-dropdown-3")
    WebElement dropdown3;
    @FindBy(id = "context-menu-3")
    WebElement contextMenu3;
    @FindBy(id = "draggable")
    WebElement src;
    @FindBy(id = "target")
    WebElement tar;
    @FindBy(tagName = "canvas")
    WebElement canvas;
    @FindBy(name = "my-text")
    WebElement textInput;
    @FindBy(name = "my-textarea")
    WebElement textArea;


    //  ***********   PAGE METHODS   **************
    public boolean isCheckbox2Selected() {
        return checkbx2.isSelected();
    }
    public void clickChkbox2() {
        new Actions(driver)
                .click(checkbx2)
                .pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }
    public boolean isRadio1Selected() {
        return radio1.isSelected();
    }
    public void clickRadio2() {
        new Actions(driver)
                .click(radio2)
                .pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }
    public boolean isRadio2Selected() {
        return radio2.isSelected();
    }
    public void contextClickDropdown2() {
        new Actions(driver)
                .contextClick(dropdown2)
                .build()
                .perform();
    }
    public boolean isContextMenu2Displayed() {
        return contextMenu2.isDisplayed();
    }
    public void doubleClickDropdown3() {
        new Actions(driver)
                .doubleClick(dropdown3)
                .build()
                .perform();
    }
    public boolean isContextMenu3Displayed() {
        return contextMenu3.isDisplayed();
    }
    public void clickOneBtn() {
        oneBtn.click();
    }
    public void clickTwoBtn() {
        twoBtn.click();
    }
    public void clickThreeBtn() {
        threeBtn.click();
    }
    public void clickNextBtn() {
        nextBtn.click();
    }
    public void clickPrevBtn() {
        prevBtn.click();
    }
    public String getBodyText() {
        return bodyText.getText();
    }
    public WebElement getImg(String imgName) {
        String imgXpath = String.format("//img[@src='img/%s.png']", imgName);
        WebElement img = driver.findElement(By.xpath(imgXpath));
        new Actions(driver)
                .moveToElement(img)
                .build()
                .perform();
        return img;
    }
    public String getImgCaption(WebElement img) {
        return driver.findElement(RelativeLocator
                .with(By.tagName("div")).near(img)).getText();
    }
    public WebElement getSrcElement() {
        return src;
    }
    public Point getSrcLocation() {
        return src.getLocation();
    }
    public Point getTarLocation() {
        return tar.getLocation();
    }
    public WebElement getTarElement() {
        return tar;
    }
    public void configOffset(WebElement src, int offset) {
        new Actions(driver)
                .dragAndDropBy(src, offset, 0)
                .dragAndDropBy(src, 0, offset)
                .dragAndDropBy(src, -offset, 0)
                .dragAndDropBy(src, 0, -offset)
                .build()
                .perform();
    }
    public void dragAndDrop(WebElement src, WebElement tar) {
        new Actions(driver)
                .dragAndDrop(src, tar)
                .build()
                .perform();
    }
    public void setFocusOnCanvas(Actions actions) {
        actions.moveToElement(canvas).clickAndHold();
    }
    public void drawCircle(Actions actions) {
        int numPoints = 15;
        int radius = 25;
        for (int i = 0; i <= numPoints; i++) {
            double angle = Math.toRadians((double) (360 * i) / numPoints);
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            actions.moveByOffset((int) x, (int) y);
        }
    }
    public void releaseCanvasFocus(Actions actions) {
        actions.release(canvas)
                .build()
                .perform();
    }
    public String getTextInputTxt() {
        return textInput.getAttribute("value");
    }
    public String getTextAreaTxt() {
        return textArea.getAttribute("value");
    }
    public void copyAndPasteInputToArea(Keys modifier) {
        Actions actions = new Actions(driver);
        actions.sendKeys(textInput, "Good-ay Mate!")
                .pause(Duration.ofSeconds(3))
                .keyDown(modifier)
                .sendKeys(textInput, "a")
                .sendKeys(textInput, "c")
                .pause(Duration.ofSeconds(3))
                .sendKeys(textArea, "v")
                .pause(Duration.ofSeconds(3))
                .build()
                .perform();

    }

}
