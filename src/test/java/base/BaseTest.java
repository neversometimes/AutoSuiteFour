package base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    public WebDriver driver;
    public DevTools devTools;
    SoftAssert sa = new SoftAssert();
    public String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
    public String lang = "es-ES";


    @BeforeMethod
    public WebDriver setUp() throws URISyntaxException {
        // path to web extension which sets background color of web pages to black
        Path extension = Paths.get(ClassLoader.getSystemResource("dark-bg.crx").toURI());

        // get target browser from CLI if given, otherwise use "chrome"
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";
        // set lang prefs for chrome/edge
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", lang);

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")){      // if headless param given on CLI
                options.addArguments("--headless");       // then run test in headless mode
            }
            options.addArguments("--use-fake-ui-for-media-stream");     // used for getUserMediaTest
            options.addArguments("--use-fake-device-for-media-stream");
            options.setAcceptInsecureCerts(true);
            options.setExperimentalOption("prefs", prefs);
            options.addExtensions(extension.toFile()); // add web extension

            driver = new ChromeDriver(options);

            devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
        } else if (browserName.contains("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (browserName.contains("headless")){      // if headless param given on CLI
                options.addArguments ("-headless");      // then run test in headless mode
            }
            options.addPreference("intl.accept_languages", lang); //pref for lang
            driver = new FirefoxDriver(options);

        } else if (browserName.contains("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (browserName.contains("headless")) {     // if headless param given on CLI
                options.addArguments("--headless");       // then run test in headless mode
            }
            options.addArguments("--use-fake-ui-for-media-stream");     // used for GetUserMediaTest
            options.addArguments("--use-fake-device-for-media-stream");
            options.setAcceptInsecureCerts(true);
            options.setExperimentalOption("prefs", prefs);
            options.addExtensions(extension.toFile()); // add web extension

            driver = new EdgeDriver(options);
        }
        driver.manage().window().setSize(new Dimension(1440, 900));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));  // basic 5s implicit wait

        return driver;
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }


    public void goToURL(String url) {
        driver.get(url);
    }

    public String getPageURL () {
        return driver.getCurrentUrl();
    }

    public String getBrowserName () {
         Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
         return cap.getBrowserName();
    }

}
