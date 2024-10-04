package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;

import java.time.Duration;
import java.util.UUID;

public class WebAuthPage {

    WebDriver driver;
    Wait<WebDriver> wait;
    HasVirtualAuthenticator virtualAuth;

    public WebAuthPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.virtualAuth = (HasVirtualAuthenticator) driver;
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(id = "input-email")
    WebElement formInput;
    @FindBy(id = "register-button")
    WebElement registerBtn;
    @FindBy(id = "login-button")
    WebElement logInBtn;
    @FindBy(className = "alert-success")
    WebElement alertSuccess;
    @FindBy(className = "main-content")
    WebElement mainContent;

    //  ***********   PAGE METHODS   **************
    public VirtualAuthenticator createVirtualAuthenticator() {
        return virtualAuth.addVirtualAuthenticator(new VirtualAuthenticatorOptions());
    }
    public void addRandomKeyStringToForm() {
        String randomID = UUID.randomUUID().toString();
        formInput.sendKeys(randomID);
    }
    public void submitFormUsingVirtualAuthCreds() {
        registerBtn.click();
        wait.until(ExpectedConditions.textToBePresentInElement(alertSuccess,
                "Success! Now try to authenticate..."));
    }
    public void logInAfterAuth() {
        logInBtn.click();
        wait.until(ExpectedConditions.textToBePresentInElement(mainContent,
                "You're logged in!"));
    }
    public String getSuccessTxt() {
        return mainContent.getText();
    }
    public void removeVirtualAuthenticator(VirtualAuthenticator vAuth) {
        virtualAuth.removeVirtualAuthenticator(vAuth);
    }

}
