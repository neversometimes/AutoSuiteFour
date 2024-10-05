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
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.slf4j.Logger;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BaseTest {

    public WebDriver driver;
    public DevTools devTools;
    SoftAssert sa = new SoftAssert();
    public String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
    public String lang = "es-ES";
    public static final Logger log = getLogger(lookup().lookupClass());
    public ChromeOptions cOptions = new ChromeOptions();
    public FirefoxOptions ffOptions = new FirefoxOptions();
    public EdgeOptions eOptions = new EdgeOptions();

    @BeforeTest
    public WebDriver setUp() throws URISyntaxException {
        // path to web extension which sets background color of web pages to black
        Path extension = Paths.get(ClassLoader.getSystemResource("dark-bg.crx").toURI());

        // get target browser from CLI if given, otherwise use "chrome"
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";

        // set browser prefs for chrome/edge
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", lang);
        prefs.put("profile.default_content_setting_values.notifications", 1);

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        if (browserName.contains("chrome")) {

            if (browserName.contains("headless")){      // if headless param given on CLI
                cOptions.addArguments("--headless");       // then run test in headless mode
            }
            cOptions.addArguments("--use-fake-ui-for-media-stream");     // used for getUserMediaTest
            cOptions.addArguments("--use-fake-device-for-media-stream");
            cOptions.setAcceptInsecureCerts(true);
            cOptions.setExperimentalOption("prefs", prefs);
            cOptions.addExtensions(extension.toFile()); // add web extension
            cOptions.setCapability(ChromeOptions.LOGGING_PREFS, logs);


            driver = new ChromeDriver(cOptions);

            devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
        } else if (browserName.contains("firefox")) {

            if (browserName.contains("headless")){      // if headless param given on CLI
                ffOptions.addArguments ("-headless");      // then run test in headless mode
            }
            ffOptions.addPreference("intl.accept_languages", lang); //pref for lang
            driver = new FirefoxDriver(ffOptions);

        } else if (browserName.contains("edge")) {

            if (browserName.contains("headless")) {     // if headless param given on CLI
                eOptions.addArguments("--headless");       // then run test in headless mode
            }
            eOptions.addArguments("--use-fake-ui-for-media-stream");     // used for GetUserMediaTest
            eOptions.addArguments("--use-fake-device-for-media-stream");
            eOptions.setAcceptInsecureCerts(true);
            eOptions.setExperimentalOption("prefs", prefs);
            eOptions.addExtensions(extension.toFile()); // add web extension

            driver = new EdgeDriver(eOptions);
        }
        driver.manage().window().setSize(new Dimension(1440, 900));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));  // basic 5s implicit wait

        return driver;
    }

    @AfterTest
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
