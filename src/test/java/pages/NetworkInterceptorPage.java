package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class NetworkInterceptorPage {


    WebDriver driver;

    public NetworkInterceptorPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************
    @FindBy(tagName = "img")
    WebElement img;


    //  ***********   PAGE METHODS   **************
    public Path loadLocalTestResource(String file) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(file).toURI());
    }
    public byte[] readFileAsBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }
    public int getWidthOfImg() {
        return Integer.parseInt(img.getAttribute("width"));
    }
    public int getHeightOfImg() {
        return Integer.parseInt(img.getAttribute("height"));
    }
    public NetworkInterceptor createNWInterceptor(byte[] byteArr) {
        // Interceptor created Route, match all http requests ending in .png,
        //  then set new response to test resource content via bytes array
        return new NetworkInterceptor(driver,
                Route.matching(req->req.getUri().endsWith(".png")).to(() -> req -> new HttpResponse()
                        .setContent(Contents.bytes(byteArr))));
    }

}
